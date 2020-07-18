import React, { Component } from 'react';
import { NavLink } from 'react-router-dom';

export default class SafetySurvey extends Component {

    render() {
        return (
             <div className="container" style={{ padding: "50px" }}>
                <h2 style={{ color: "purple" }}>My Safety123:</h2>

                <p style={{ color: "black" }}>
                    This section of I-DECIDE walks you through some<br />
                    questions and activities to help you think about<br />
                    your safety.
                </p>

			

                <div style={{ padding: "10px" }}>
                    <NavLink to="/surveyComponent/safetyQuestions">
                        <button >Next</button>
                    </NavLink>
                </div >
            </div >




        );
    }
}