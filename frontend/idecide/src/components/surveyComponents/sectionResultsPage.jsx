import React from "react";

import { Card, Accordion } from "react-bootstrap";
import { Link } from "react-router-dom";
import PrimaryButton from "../reusableComponents/PrimaryButton";

/**
 * This component will show a) the 'feedback' from the survey and b) the answers
 * filled in by the user.
 */

export default function SectionResultsPage(props) {
  var feedback =
    props.feedbackCategory === null ? (
      ""
    ) : (
      <React.Fragment>
        <Card.Title>
          Feedback: <b>{props.feedbackCategory}</b>
        </Card.Title>
        <img src={props.feedbackImage} alt="" width="30%" />
        <Card.Text style={{ fontSize: "18px" }}>{props.feedbackText}</Card.Text>
      </React.Fragment>
    );

  return (
    <div>
      <Card className="surveyIntroCard" style={{ width: "80%" }}>
        <Card.Body>
          <h1 className="text-center" style={{ color: "#9572A4" }}>
            Thank you for completing this section
          </h1>

          {feedback}
        </Card.Body>
      </Card>
    </div>
  );
}
