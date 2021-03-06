import React, { Component } from "react";
import { Button, Slider, Form } from "antd";

import { CSSTransition, TransitionGroup } from "react-transition-group";
import "./cards.css";
import "antd/dist/antd.css";
import LoadingSpinner from "../reusableComponents/loading";
import SortableComponent from "../RankingComponent/testSortable";
import PrimaryButton from "./../reusableComponents/PrimaryButton";
import TextField from "@material-ui/core/TextField";
import testJson from "../../SurveyJsons/mySituation.json";
import { Typography } from "@material-ui/core";

export default class CardDeck extends Component {
  constructor(props) {
    super(props);
    this.state = {
      questions: null,
      questionLen: null,
      algorithmRelatedQuestion: null,
      enginRule: null,
      feedback: null,
      fadeAwayState: false,
      skiped: { name: "skpied" },
      result: [],
    };
    this.changeChild = React.createRef();
  }

  componentDidMount() {
    let questionsIn = this.props.section.questions;
    // let questionLength = questionsIn.length;
    let questionCount = 1;

    questionsIn.forEach((questionEach) => {
      questionEach["questionIndex"] = questionCount;
      questionCount = questionCount + 1;
    });

    this.setState({
      questions: questionsIn,
      questionLen: this.props.section.questions.length,
      algorithmRelatedQuestion: this.props.section.algorithmRelatedQuestion,
      enginRule: this.props.section.enginRule,
      feedback: this.props.section.feedback,
    });
  }

  handleClick(item) {
    const _this = this;
    const questions = this.state.questions;
    this.setState(
      {
        questions: questions.filter(
          (ite) => ite.questionId !== item.questionId
        ),
        fadeAwayState: true,
      },
      () => {
        if (this.state.questions.length === 0) {
          //let the upper components know that we've completed the card deck! We can continue!
          this.props.canProgress();
        }
      }
    );
    setTimeout(() => {
      _this.setState({
        fadeAwayState: false,
        clickTapStatus: true,
      });
    }, 400);
  }

  handleResult(item, result) {
    //here will handle the result !

    this.props.handleAnswer(item.questionId, result);

    var currentResults = this.state.result;
    currentResults.push({
      questionId: item.questionId,
      answer: result,
    });
    this.setState({
      result: currentResults,
    });

    this.handleClick(item);

    // if (item.questionId == this.state.questionLen) {
    //   JsonRuleEngine(
    //     this.state.result,
    //     this.state.algorithmRelatedQuestion,
    //     this.state.enginRule,
    //     this.handleCASResult
    //   );
    // }
    // this.props.handleNav(1);
  }

  // handleCASResult = (casResult) => {
  //   this.setState({
  //     CasResult: casResult,
  //   });
  // };

  handleSubmit = (item, value) => {
    //Make a network call somewhere

    this.handleResult();
  };

  questionTypeController(item) {
    if (item.questionType === "singleSelection") {
      return (
        <div className="questionContainer">
          <div className="composite-scale-container">
            <div className="option-container">
              {item.selectionOptions.map((option, index) => (
                <button
                  key={index}
                  onClick={() => this.handleResult(item, option)}
                  className="composite-option-button"
                >
                  <span className="composite-circle top"></span>
                  <span className="composite-label bottom">{option}</span>
                </button>
              ))}
            </div>
          </div>
        </div>
      );
    } else if (item.questionType === "slider") {
      let silderresult = 0;

      return (

        < div className="questionContainer" >
          <Slider
            ref={`slider-${item.questionId}`}
            className="ranger-container-silder"
            defaultValue={5}
            min={0}
            max={item.sliderMaxValue}
            onAfterChange={(event) => {
              silderresult = event;
            }}
          />


          <div className="label-container">

            <span className="label" >
              {item.sliderMinValue}
            </span>
            <span className="label" >
              {item.sliderMaxValue}
            </span>
          </div>

          {/* Need to discuss about the button locations */}
          <div className="button-container">
            <PrimaryButton
              onClick={() => this.handleResult(item, this.state.skiped)}
            >
              Skip
            </PrimaryButton>
            <PrimaryButton
              onClick={() => this.handleResult(item, silderresult)}
            >
              CONFIRM
            </PrimaryButton>
          </div>
        </div >
      );
    } else if (item.questionType === "yesOrNo") {
      return (
        <div className="questionContainer">
          <div className="button-container">
            <PrimaryButton
              gradient="aqua"
              style={{ border: "none", "borderradius": "15px" }}
              onClick={() => this.handleResult(item, "No")}
            >
              No
            </PrimaryButton>
            <PrimaryButton
              style={{ border: "none", "borderradius": "15px" }}
              onClick={() => this.handleResult(item, "Yes")}
            >
              Yes
            </PrimaryButton>
          </div>
        </div>
      );
    } else if (item.questionType === "singleSelectionVertical") {
      return (
        <div className="questionContainer">
          <div className="composite-scale-container">
            <div className="option-container-vertical">
              {item.selectionOptions.map((option, index) => (
                <button
                  key={index}
                  onClick={() => this.handleResult(item, option)}
                  className="composite-option-button"
                >
                  <span className="composite-circle top"></span>
                  <span className="composite-label bottom">{option}</span>
                </button>
              ))}
            </div>
          </div>
        </div>
      );
    } else if (item.questionType === "ranking") {
      return (
        <div className="questionContainer">
          <SortableComponent
            options={item.selectionOptions}
            ref={this.changeChild}
          />
          <div className="button-container">
            <PrimaryButton
              onClick={() => {
                this.handleResult(item, this.changeChild.current.state.items);
              }}
            >
              CONFIRM
            </PrimaryButton>
          </div>
        </div>
      );
    } else if (item.questionType === "longAnswer") {
      return (
        <div style={{ width: "80%" }}>
          <Form
            onFinish={(value) => this.handleResult(item, value["contents"])}
          >
            <Form.Item name="contents">
              <TextField
                inputProps={{
                  maxLength: `${item.answerLength}`,
                }}
                id="outlined-textarea"
                placeholder="Enter your response"
                multiline
                fullWidth
                variant="outlined"
              />
            </Form.Item>
            <Form.Item>
              <PrimaryButton type="submit">submit</PrimaryButton>
            </Form.Item>
          </Form>
        </div>
      );
    } else {
      return (
        <div className="questionContainer">
          Error, question type not supported.
        </div>
      );
    }
  }

  handleSections = async (direction) => {
    await this.props.handleNav(direction);

    //TODO: remove eg 'enginRule' below... in fact I think can remove this function?
    this.setState({
      questions: this.props.section.questions,
      questionLen: this.props.section.questions.length,
      algorithmRelatedQuestion: this.props.section.algorithmRelatedQuestion,
      enginRule: this.props.section.enginRule,
      feedback: this.props.section.feedback,
    });
  };

  render() {
    var { questions, questionLen } = this.state;

    let fadeAwayState = this.state.fadeAwayState;
    if (questions == null) {
      return (
        <div className="cards-container cards-container-checkbox">
          <LoadingSpinner />
        </div>
      );
    }


    const ItemList = (
      <TransitionGroup>
        {questions.map((item, index) => (
          <CSSTransition
            timeout={600}
            unmountOnExit
            classNames="fade"
            key={item.questionId}
          >
            <div
              className={`card-container ${index >= 4 ? "hide" : ""} 
              ${fadeAwayState && index === 0 ? " opaque" : ""}
              `}
              key={index}
            >
              {/* MARKER: questionIndex Counter */}
              <div className="counter" style={{ marginTop: 40 }}>
                {/* <h5 className="current">{item.questionId}</h5> */}
                <h5 className="current">{item.questionIndex}</h5>
                {/* <h5>/{questions.length + parseInt(item.questionId) - 1}</h5> */}
                <h5>/{questionLen}</h5>
              </div>

              <Typography gutterBottom variant="h6">
                {item.questionText}
              </Typography>
              {this.questionTypeController(item)}
            </div>
          </CSSTransition>
        ))}
      </TransitionGroup>
    );

    return (
      <div className="cards-container cards-container-checkbox">
        <div className="cards-wrapper">
          <div className="cards-list">
            {ItemList}
            <div style={{ color: "white" }}>
              Section Complete. <br /> Press "Next" to continue.
            </div>
          </div>
        </div>
      </div>
    );
  }
}
