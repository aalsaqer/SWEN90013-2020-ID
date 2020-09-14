import React, { useState, useEffect, createContext } from 'react';
import {
	Box,
	Button,
	Collapse,
	Dialog,
	DialogTitle,
	DialogContent,
	DialogContentText,
	TextField,
	FormControl,
	InputLabel,
	MenuItem,
	Select,
	DialogActions,
	Card,
	CardContent,
	CardHeader,
	Divider,
	Typography,
	IconButton,
	Grid
} from '@material-ui/core';
import DeleteForeverOutlinedIcon from '@material-ui/icons/DeleteForeverOutlined';

import { getSurveyById, editSurvey } from '../../../../../API/surveyAPI';
import EditIcon from '@material-ui/icons/Edit';
import QuestionDetails from './QuestionDetails';
import Alert from '@material-ui/lab/Alert';
import ExpandMoreRoundedIcon from '@material-ui/icons/ExpandMoreRounded';
import ControlPointRoundedIcon from '@material-ui/icons/ControlPointRounded';

export const t = 4;
export const QuestionContext = createContext();

const SectionQuestions = (props) => {
	const [ openAddQuestion, setOpenAddQuestion ] = useState(false);
	const [ isOpen, setIsOpen ] = React.useState(false);

	// console.log(props);
	const [ open, setDMOpen ] = React.useState(false); //control of adding new survey
	//const [ isOpen, setOpen ] = React.useState(false);
	const [ openAlert, setOpen ] = React.useState(false);
	const [ openGreen, setOpenGreen ] = React.useState(false);
	const [ error, setError ] = React.useState();
	const [ values, setValues ] = React.useState({
		title: props.data.sectionTitle,
		descrpition: props.data.sectionIntroduction,
		question: '',
		option1: 'Never',
		option2: 'Once',
		option3: 'Several Times',
		option4: 'Once A Month',
		option5: 'Once A Week',
		option6: 'Daily'
	});

	const [ type, setType ] = React.useState('');

	const handleTypeChange = (event) => {
		setType(event.target.value);
	};

	const handleChange = (prop) => (event) => {
		setValues({ ...values, [prop]: event.target.value });
	};

	const handleOpen = () => {
		setDMOpen(true);
	};

	const handleClose = () => {
		setDMOpen(false);
	};

	function handleView() {
		setIsOpen((prev) => !prev);
	}

	const handleQClose = () => {
		setOpenAddQuestion(false);
	};

	const handleAddQuestion = () => {
		setOpenAddQuestion(true);
	};

	const AddQuestion = async () => {
		if (openGreen) {
			window.location.href = './surveyId=' + props.surveyId;
		} else {
			let questions = props.data.questions;
			let questionIndex = 0;
			if (typeof questions.length !== 'undefined') questionIndex = questions.length;
			var newSliderQuestion = {
				questionIndex: questionIndex,
				questionId: values.questionId,
				questionText: values.question,
				questionType: type,
				sliderDefaultValue: 5,
				sliderMaxValue: 10,
				sliderMinValue: 0
			};

			var newYNQuestion = {
				questionIndex: questionIndex,
				questionId: values.questionId,
				questionText: values.question,
				questionType: type,
				selectionOptions: [
					{
						name: values.option1,
						weight: '0'
					},
					{
						name: values.option2,
						weight: '0.2'
					},
					{
						name: values.option3,
						weight: '0.4'
					},
					{
						name: values.option4,
						weight: '0.6'
					},
					{
						name: values.option5,
						weight: '0.8'
					},
					{
						name: 'Daily',
						weight: '1'
					}
				]
			};
			questions.push(newMCQuestion);
		}

			if (type == 'slider') {
				questions.push(newSliderQuestion);
			} else if (type == 'yesorno') {
				questions.push(newYNQuestion);
			} else {
				var newMCQuestion = {
					questionIndex: questionIndex,
					questionId: values.questionId,
					questionText: values.question,
					questionType: type,
					selectionOptions: [
						{
							name: values.option1,
							weight: '0'
						},
						{
							name: values.option2,
							weight: '0.2'
						},
						{
							name: values.option3,
							weight: '0.4'
						},
						{
							name: values.option4,
							weight: '0.6'
						},
						{
							name: values.option5,
							weight: '0.8'
						},
						{
							name: values.option6,
							weight: '1'
						}
					]
				};
				questions.push(newMCQuestion);
			}

			let sections = props.sections;

		await editSurvey(readyData)
			.then(() => {
				setOpenAddQuestion(false)
			})
			.catch(() => {
				setOpen(true);
				setError(error + '');
			});

	};

	const UpdateSection = async (event) => {

		event.preventDefault();

		const formIn = new FormData(event.target);
		var formObject = {};
		formIn.forEach((value, key) => {
			formObject[key] = value;
		});

		let sections = props.sections;

		var modifiedSection = {

			sectionTitle: values.title,
			sectionIntroduction: values.descrpition,
			sectionId: props.data.sectionId,
			sectionIndex: props.data.sectionIndex,
			questions: props.data.questions
		};

		sections.splice(parseInt(props.data.sectionIndex), 1, modifiedSection);
		console.log(sections);


		var readyData = JSON.stringify({
			surveyId: props.surveyId,
			surveySections: sections
		});

		await editSurvey(readyData)
			.then((data) => {
				setDMOpen(false);
				props.handleShow();

			})
			.catch(() => {
				setOpen(true);
				setError(error + '');
			});
	};

	const deleteSection = async () => {
		if (openGreen) {
			window.location.href = './surveyId=' + props.surveyId;
		}

		let sections = props.sections;
		sections.splice(props.data.sectionIndex, 1);
		sections.map((item, index) => {
			item.sectionIndex = index;
		});

		var readyData = JSON.stringify({
			surveyId: props.surveyId,
			surveySections: sections
		});

		await editSurvey(readyData)
			.then((data) => {
				window.location.reload();
			})
			.catch(() => {
				setOpen(true);
				setError(error + '');
			});
	};

	return (
		<div>
			<Box p={2}>
				<Card>
					<CardHeader
						action={
							<div>
								<IconButton
									style={{ width: 50 }}
									color="secondary"
									aria-label="add an alarm"
									onClick={handleOpen}
								>
									<EditIcon color="primary" />
								</IconButton>
								<IconButton
									style={{ width: 50 }}
									color="secondary"
									aria-label="add an alarm"
									onClick={handleAddQuestion}
								>
									<ControlPointRoundedIcon color="primary" />
								</IconButton>

								<IconButton
									style={{ width: 50 }}
									color="secondary"
									aria-label="add an alarm"
									onClick={handleView}
								>
									<ExpandMoreRoundedIcon color="primary" />
								</IconButton>
							</div>
						}
						//   subheader="Description"
						title={props.data.sectionTitle}
					/>
					<Divider />
					<CardContent>
						<Box display="flex" p={1}>
							<Typography variant="subtitle1" gutterBottom>
								{props.data.sectionIntroduction}
							</Typography>
						</Box>
					</CardContent>
				</Card>
				<Collapse in={isOpen}>
					{typeof props.data.questions !== 'undefined' ? (
						props.data.questions.map((question) => (
							<Box>
								<QuestionDetails
									surveyID={props.surveyId}
									data={question}
									currentSection={props.sections}
									questions={props.data.questions}
								/>
							</Box>
						))
					) : (
						<div>No questions</div>
					)}
				</Collapse>
			</Box>
			{/**  This window is used for updating section */}
			<Dialog open={open} onClose={handleClose} aria-labelledby="max-width-dialog-title" maxWidth="lg" fullWidth>
				<DialogTitle id="form-dialog-title">Section</DialogTitle>
				<DialogContent>
					<Collapse in={!openGreen}>
						<DialogContentText>Please input the title and description for the section.</DialogContentText>

						<TextField
							id="outlined-multiline-flexible"
							required
							fullWidth
							value={values.title}
							onChange={handleChange('title')}
							label="Title"
							variant="outlined"
						/>
						<DialogContentText>value={values.title}</DialogContentText>
						<TextField
							id="outlined-multiline-flexible"
							multiline
							fullWidth
							required
							value={values.descrpition}
							onChange={handleChange('descrpition')}
							rows={4}
							label="Description"
							variant="outlined"
						/>
					</Collapse>
				</DialogContent>
				<DialogContent>
					<Collapse in={openAlert}>
						<Alert severity="error">{error}</Alert>
					</Collapse>
					<Collapse in={openGreen}>
						<Alert severity="success">Update Section Successfully!</Alert>
					</Collapse>
				</DialogContent>
				<DialogActions>
					<Collapse in={!openGreen}>
						<IconButton color="secondary" aria-label="add an alarm" onClick={deleteSection}>
							<DeleteForeverOutlinedIcon fontSize="large" />
						</IconButton>
					</Collapse>
					<Collapse in={!openGreen}>
						<Button onClick={handleClose} color="primary">
							Cancel
						</Button>
						</Collapse>
						<Button type="submit" color="primary">
							Confirm
						</Button>
					</DialogActions>
				</form>
			</Dialog>

			{/**  This window is used for adding new question */}
			<Dialog
				open={openAddQuestion}
				onClose={handleQClose}
				aria-labelledby="max-width-dialog-title"
				//	fullWidth="md"
				maxWidth="lg"
				fullWidth
			>
				<DialogTitle id="form-dialog-title">New Question</DialogTitle>
				<DialogContent>
					<Collapse in={!openGreen}>
						<DialogContentText>Please fill in details for the new question.</DialogContentText>
						<TextField
							id="outlined-multiline-flexible"
							required
							value={values.questionId}
							type="number" min="0" step="1"
							onChange={handleChange('questionId')}
							label="questionId"
							variant="outlined"
						/>
						<Box p={1} />
						<TextField
							id="outlined-multiline-flexible"
							required
							fullWidth
							value={values.question}
							onChange={handleChange('question')}
							label="Question Description"
							variant="outlined"
						/>
						<Box p={1} />
						{/* <DialogContentText>value={values.question}</DialogContentText> */}
						<FormControl fullWidth>
							<InputLabel id="demo-simple-select-label">Type</InputLabel>
							<Select
								labelId="demo-simple-select-label"
								id="demo-simple-select"
								value={type}
								onChange={handleTypeChange}
							>
								<MenuItem value={'slider'}>Slider </MenuItem>
								<MenuItem value={'singleSelection'}>MultiChoice</MenuItem>
								{/* <MenuItem value={'yesorno'}>Yes/No</MenuItem> */}
							</Select>
						</FormControl>
						<Box p={1}>
							<div>
								{type === 'singleSelection' ? (
									<div>
										<TextField
											label="option1"
											id="outlined-multiline-flexible"
											variant="outlined"
											value={values.option1}
											onChange={handleChange('option1')}
										/>
										<TextField
											label="option2"
											id="outlined-multiline-flexible"
											variant="outlined"
											value={values.option2}
											onChange={handleChange('option2')}
										/>
										<TextField
											label="option3"
											id="outlined-multiline-flexible"
											variant="outlined"
											value={values.option3}
											onChange={handleChange('option3')}
										/>
										<TextField
											label="option4"
											id="outlined-multiline-flexible"
											variant="outlined"
											value={values.option4}
											onChange={handleChange('option4')}
										/>
										<TextField
											label="option5"
											id="outlined-multiline-flexible"
											variant="outlined"
											value={values.option5}
											onChange={handleChange('option5')}
										/>
										<TextField
											label="option6"
											id="outlined-multiline-flexible"
											variant="outlined"
											value={values.option6}
											onChange={handleChange('option6')}
										/>
									</div>
								) : null}
							</div>
						</Box>
					</Collapse>
				</DialogContent>
				<DialogContent>
					<Collapse in={openAlert}>
						<Alert severity="error">{error}</Alert>
					</Collapse>
					<Collapse in={openGreen}>
						<Alert severity="success">Update Section Successfully!</Alert>
					</Collapse>
				</DialogContent>
				<DialogActions>
					<Collapse in={!openGreen}>
						<Button onClick={handleQClose} color="primary">
							Cancel22
						</Button>
					</Collapse>
					<Button onClick={AddQuestion} color="primary">
						Confirm22
					</Button>
				</DialogActions>
			</Dialog>
		</div>
	);
};

export default SectionQuestions;
