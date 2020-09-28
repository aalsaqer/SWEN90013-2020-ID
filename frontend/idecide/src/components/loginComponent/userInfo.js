import React from "react";

import { getAllAdmins } from "../../API/loginAPI";
import LoadingSpinner from "../reusableComponents/loadingSpinner";
import PrimaryButton from "../reusableComponents/PrimaryButton";
import { getResultByUser } from "../../API/resultAPI";

export default class UserInfo extends React.Component {
    constructor(props) {
        super();
        this.state = {
            userId: localStorage.getItem("userId"),
            token: localStorage.getItem("token"),
            history: null
        };
    }

    handleLogOut = () => {
        localStorage.clear();
        window.location.replace("/")
    }

    handleHistory = async () => {
        const history = await getResultByUser(this.state.userId);
        console.log(225, this.state.userId, history);
        this.setState({
            history: history
        });
    }

    render() {
        const { history } = this.state;
        {
            return (
                <div>
                    <PrimaryButton onClick={this.handleLogOut}>
                        Log Out</PrimaryButton>
                    <PrimaryButton onClick={this.handleHistory}>
                        Complete History</PrimaryButton>
                    {history === null ? null :
                        JSON.stringify(history)}
                </div>
            );
        }
    }
}
