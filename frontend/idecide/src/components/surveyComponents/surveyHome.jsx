import React, { Component } from "react";
import SurveyControl from "./surveyControl";
import "../../CSS/survey.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "bootstrap-css-only/css/bootstrap.min.css";
import "mdbreact/dist/css/mdb.css";
import CardDesk from "../CardDeskCompoent/cardDeck";
import LoadingSpinner from "../reusableComponents/loadingSpinner";
import {
  getUserResults,
  getAllSurveys,
  getStaticImageUrlFromName,
} from "../../API/surveyAPI";
import SurveySelectionButton from "./surveySelectionButton";
import SurveyResultsPage from "./surveyResultsPage";

/**
 * The parent component of all survey pages. This component:
 * a) fetches all available surveys from the server
 * b) displays all available surveys in a UI
 * c) fetches all previous completions if logged in (INCOMPLETE)
 * d) displays all previous completions in a UI for user to review
 * Once a survey is selected, the ID is passed to a SurveyControl. SurveyHome
 * doesn't fetch the survey itself or post the results.
 */
export default class SurveyHome extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loaded: false,
      actionPlan: "",
      currentState: "menu", // ["menu","survey","completion"]
      currentResults: undefined, // when in "completion" state, holds data of completion being viewed
      currentSurveyId: -1, // when in "survey" state, ID of current survey
      allSurveys: {}, // pulled from the API, list of surveys available
      surveyCompletions: [], // pulled from the API, all previous completions
    };
    this.componentDidMount = this.componentDidMount.bind(this);
    this.startSurvey = this.startSurvey.bind(this);
  }

  async componentDidMount() {
    var userId = this.props.userId;
    if (userId === undefined) {
      console.log(
        "no user ID passed in as prop to surveyHome... using Id =92138918723"
      );
      userId = 92138918723;
    }
    var surveys = await getAllSurveys();
    //note, need to fix CORS issues with the getUserResults
    // var completions = await getUserResults(userId);

    this.setState({
      allSurveys: surveys.data,
      loaded: true,
      userId: userId,
    });
  }

  startSurvey(surveyId) {
    this.setState({
      currentState: "survey",
      currentSurveyId: surveyId,
    });
  }

  completeHandler = (surveyResults, actionPlan) => {
    this.setState((prevState) => ({
      currentResults: surveyResults,
      surveyCompletions: prevState.surveyCompletions.push(surveyResults),
    }));
  };

  handleCardDesk = () => {
    this.setState({
      showCardDesk: !this.state.showCardDesk,
    });
  };

  render() {
    const { currentState, actionPlan, currentResults } = this.state;

    if (currentState === "survey") {
      return (
        <SurveyControl
          surveyId={this.state.currentSurveyId}
          userId={this.state.userId}
          completeHandler={this.completeHandler}
        />
      );
    } else if (currentState === "menu" && this.state.loaded) {
      return (
        <div className="container" className="padding10">
          <div>
            <h2 style={{ color: "purple" }}>Help Me Decide</h2>
            <br />
            <h6>There are three modules below.</h6>
            <h6>The 'My Relationship' module is optional,</h6>
            <h6>
              but you must complete 'Safety' and 'Priorities' before you can
              continue.
            </h6>
            <h6>
              Click 'Next' in the bottom right corner when you are finished.
            </h6>
          </div>
          <br />

          <div>
            {this.state.allSurveys.map((survey) => (
              <div key={survey.surveyId} className="surveyIcon">
                <SurveySelectionButton
                  icon={getStaticImageUrlFromName(survey.surveyImageName)}
                  completed="false"
                  surveyTitle={survey.surveyTitle}
                  shortSurveyDescription={survey.surveyIntroduction}
                  handleClick={() => {
                    this.startSurvey(survey.surveyId);
                  }}
                />
              </div>
            ))}
          </div>
        </div>
      );
    } else if (currentState === "completion") {
      //viewing a previous completion
      return <SurveyResultsPage />;
    } else {
      return <LoadingSpinner />;
    }
  }
}