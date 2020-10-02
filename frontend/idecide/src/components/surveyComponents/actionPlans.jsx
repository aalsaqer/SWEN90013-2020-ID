import React, { Component } from "react";
import { Card } from "react-bootstrap";
import PrimaryButton from "../reusableComponents/PrimaryButton";
import evaluateFeedback from "../RuleEngine/evaluateFeedback";
import { Link } from "react-router-dom";
import { Table, Button, Accordion, CardDeck, Modal } from "react-bootstrap";
import Grid from "antd/lib/card/Grid";
import "../../CSS/Modal.css";
var rules = require("../../SurveyJsons/actionPlanAlgorithm.json");
var planHtmls = require("../../SurveyJsons/actionPlanHtml.json");

export default class ActionPlans extends Component {
  constructor(props) {
    super(props);
    this.state = { ModalShow: false, ModalBody: undefined };
    this.addResultsToFacts();
    this.determineActionPlans();
    this.determineActionPlans = this.determineActionPlans.bind(this);
  }

  addResultsToFacts() {
    //facts for use in algorithm, fill with defaults
    var facts = {
      HaveChildren: "Yes",
      Intention: "I intend to leave the relationship",
      Priority: "Safety",
    };
    var results = JSON.parse(localStorage.getItem("prevCompletions"));
    console.log("results are in", results);
    //TODO: need to add the parsing for the priorities
    for (var survey of results) {
      for (var question of survey.questions) {
        if (question !== null) {
          if (
            question.questionText ===
              "What are your intentions regarding your relationship?" &&
            question.questionAnswer.length !== 0
          ) {
            switch (question.questionAnswer[0]) {
              case "I intend to stay in the relationship":
                facts.Intention = "STAY";
                break;
              case "I intend to leave the relationship":
                facts.Intention = "LEAVE";
                break;
              case "I have already left the relationship":
                facts.Intention = "LEFT";
                break;
              default:
                facts.Intention = "LEAVE";
            }
          }
          if (
            question.questionText ===
              "Do you have any dependent children or step-children under the age of 18?" &&
            question.questionAnswer.length !== 0
          ) {
            facts.HaveChildren = question.questionAnswer[0];
          }
          if (
            question.questionText ===
              "Please rank the following from most important to you at the top to least important at the bottom" &&
            question.questionAnswer.length !== 0
          ) {
            facts.Priority = question.questionAnswer[0];
          }
        }
      }
    }
    if (facts.HaveChildren === "Yes" && facts.Priority === "Children") {
      facts.Priority = "Safety";
    }
    localStorage.setItem("actionPlanFacts", JSON.stringify(facts));
    console.log("straight facts", facts);
  }

  determineActionPlans() {
    var actionPlanFacts = JSON.parse(localStorage.getItem("actionPlanFacts"));
    var factsContainer = [];
    for (var key in actionPlanFacts) {
      factsContainer.push({ factName: key, fact: actionPlanFacts[key] });
    }
    console.log("facts container is", factsContainer);

    evaluateFeedback(rules, factsContainer).then((result) => {
      this.setState({ plan: result.events[0].params });
      console.log(result.events[0]);
    });
  }
  handleModalShow = () => {
    this.setState({ ModalShow: !this.state.ModalShow });
  };

  render() {
    return (
      <div>
        <Modal
          show={this.state.ModalShow}
          onHide={() => this.handleModalShow()}
          dialogClassName="main-modal"
          aria-labelledby="example-custom-modal-styling-title"
        >
          <Modal.Header closeButton>
            <Modal.Title id="example-custom-modal-styling-title">
              Custom Modal Styling
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div dangerouslySetInnerHTML={this.state.ModalBody}></div>
          </Modal.Body>
        </Modal>
        <div className="surveyIntroCard" style={{ width: "80%" }}>
          <h1 className="text-center" style={{ color: "#9572A4" }}>
            Action Plan
          </h1>
          <p style={{ fontSize: "20px" }}>
            Based on your responses, we have recommended strategies to help you
            deal with your situation. There is additional help, resources and
            recommendations in the "More Strategies" section.
          </p>
          <Accordion>
            <Card
              key={1}
              style={{
                background: "rgba(54, 25, 25, 0.00004)",
                border: "0",
                boxShadow: "none",
              }}
            >
              <Accordion.Toggle
                as={Card.Header}
                eventKey={1 + 1} // doesn't work with index of 0 for some reason?
                style={{
                  background: "linear-gradient(40deg, #ff6ec4, #7873f5)",
                  color: "white",
                  "font-size": "25px",
                }}
              >
                {/* {planHtmls[plan].description} */}
                Safety
              </Accordion.Toggle>

              <Accordion.Collapse eventKey={1 + 1}>
                <CardDeck>
                  <div
                    style={{
                      display: "grid",
                      "grid-template-columns": "repeat(3,1fr)",
                      gap: "45px",
                      margin: "20px",
                    }}
                  >
                    {this.state.plan &&
                      this.state.plan.map((plan, index) => {
                        var html = { __html: planHtmls[plan].strategyHtml };

                        return (
                          <Card>
                            <Card.Body>{planHtmls[plan].description}</Card.Body>

                            <Card.Footer>
                              <PrimaryButton
                                gradient="purple-gradient"
                                rounded
                                className="purple-gradient"
                                onClick={() => {
                                  this.handleModalShow();
                                  this.setState({ ModalBody: html });
                                }}
                              >
                                view more
                              </PrimaryButton>
                            </Card.Footer>
                          </Card>
                        );
                      })}
                  </div>
                </CardDeck>
              </Accordion.Collapse>
            </Card>
          </Accordion>
          <Link to="/surveyComponent">
            <PrimaryButton>Go back home</PrimaryButton>
          </Link>
        </div>
      </div>
    );
  }
}
/* <div>
          
              <Table striped bordered hover>
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Action Plan</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  {this.state.plan &&
                    this.state.plan.map((plan, index) => (
                      <tr>
                        <td>{index}</td>
                        <td>{plan}</td>
                        <td>
                          <Button variant="outline-success">View</Button>
                        </td>
                      </tr>
                    ))}
                </tbody>
              </Table>
              
            </Card.Body>
          
        </div> */
