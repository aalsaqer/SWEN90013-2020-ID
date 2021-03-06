// this component is rendered for each question of a section, has an 'edit' button that allows changing the question content
import React from "react";
import PropTypes from "prop-types";
import EditIcon from "@material-ui/icons/Edit";
import { editSurvey } from "../../../../../API/surveyAPI";
import DeleteForeverOutlinedIcon from "@material-ui/icons/DeleteForeverOutlined";
import {
  Button,
  Box,
  Card,
  Collapse,
  Paper,
  IconButton,
  TextField,
  DialogContentText,
  CardContent,
  Divider,
  Grid,
  Typography,
} from "@material-ui/core";

const QuestionDetails = (props) => {
  const [isOpen, setOpen] = React.useState(false);
  const [question, setQuestion] = React.useState();

  const UpdateQuesion = async (event) => {
    event.preventDefault();

    const dataIn = new FormData(event.target);
    var questionObject = {};
    dataIn.forEach((value, key) => {
      questionObject[key] = value;
    });

    let sections = props.currentSection;
    props.data.questionText = questionObject.updatedQuestion;
    props.questions.splice(parseInt(props.data.questionIndex), 1, props.data);

    var readyData = JSON.stringify({
      surveyId: props.surveyID,
      surveySections: sections,
    });

    await editSurvey(readyData)
      .then((response) => {
        if (response.data.code == 200) {
          alert("Question description has been updated!");
          window.location.reload();
        } else {
          alert(response.data.message);
        }
      })
      .catch((error) => {
        alert(error + "");
      });
  };

  const deleteQuestion = async (event) => {
    let sections = props.currentSection;

    props.questions.splice(parseInt(props.data.questionIndex), 1);
    props.questions.map((item, index) => {
      item.questionIndex = index;
    });

    var readyData = JSON.stringify({
      surveyId: props.surveyID,
      surveySections: sections,
    });

    await editSurvey(readyData).then((data) => {
      alert("Question has been updated deleted!");
      window.location.reload();
    });
  };

  const QuestionDetail = () => {
    return (
      <div>
        <form onSubmit={UpdateQuesion}>
          <DialogContentText>
            Please input the description for the question.
          </DialogContentText>
          <TextField
            id="outlined-multiline-flexible"
            name="updatedQuestion"
            multiline
            fullWidth
            required
            defaultValue={props.data.questionText}
            rows={2}
            label="Description"
            variant="outlined"
          />
          <IconButton
            color="secondary"
            aria-label="add an alarm"
            onClick={deleteQuestion}
          >
            <DeleteForeverOutlinedIcon fontSize="large" />
          </IconButton>
          <Button type="submit" color="primary">
            Confirm
          </Button>
        </form>
      </div>
    );
  };
  return (
    <div>
      <Box p={1}>
        <Paper>
          <Grid
            container
            container
            direction="row"
            justify="flex-start"
            alignItems="center"
          >
            <Grid item xs={10}>
              {"Q" + (props.index + 1) + " :  " + props.data.questionText}
            </Grid>
            <Grid item xs={1}>
              <Typography color="textSecondary" gutterBottom variant="body1">
                {props.data.questionType}
              </Typography>
            </Grid>
            <Grid item xs={1}>
              <IconButton
                style={{ width: 50 }}
                color="secondary"
                aria-label="add an alarm"
                onClick={() => {
                  setOpen((prev) => !prev);
                }}
              >
                <EditIcon color="primary" fontSize="small" />
              </IconButton>
            </Grid>
          </Grid>
        </Paper>

        <Card>
          {/**		<CardContent style={{ height: 70 }}>
						<Grid container direction="row" justify="flex-start" alignItems="center">
							<Grid item xs={7}>
								{'Q' + (props.index + 1) + ' :  ' + props.data.questionText}
							</Grid>
							<Grid item xs={1}>
								<Typography color="textSecondary" gutterBottom variant="body1">
									{props.data.questionType}
								</Typography>
							</Grid>
							<Grid item xs={1}>
								<IconButton
									style={{ width: 50 }}
									color="secondary"
									aria-label="add an alarm"
									onClick={() => {
										setOpen((prev) => !prev);
									}}
								>
									<EditIcon color="primary" />
								</IconButton>
							</Grid>
						</Grid>
					</CardContent> */}
          <Divider />
          <Collapse in={isOpen}>
            <CardContent>
              <QuestionDetail />
            </CardContent>
          </Collapse>
        </Card>
      </Box>
    </div>
  );
};

QuestionDetails.propTypes = {
  className: PropTypes.string,
};

export default QuestionDetails;
