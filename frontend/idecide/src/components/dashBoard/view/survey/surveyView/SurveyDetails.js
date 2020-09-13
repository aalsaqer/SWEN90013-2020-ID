import React, { useState, useEffect, createContext } from 'react';
import { Box, Button, Collapse, makeStyles } from '@material-ui/core';
import { Card, CardContent, CardHeader, Divider, Typography, IconButton, Grid } from '@material-ui/core';
import { getSurveyById, editSurvey } from '../../../../../API/surveyAPI';
import EditIcon from '@material-ui/icons/Edit';
import QuestionDetails from './QuestionDetails';
import NewSectionComp from '../surveyEdit/NewSectionComp';

const useStyles = makeStyles((theme) => ({
	root: {
		backgroundColor: theme.palette.background.dark,
		minHeight: '100%',
		paddingBottom: theme.spacing(3),
		paddingTop: theme.spacing(3)
	}
}));

const blogInfo = {
	eact: {
		post: 'Learn useContext Hooks',
		author: 'Adhithi Ravichandran'
	},
	GraphQL: {
		post: 'Learn GraphQL Mutations',
		author: 'Adhithi Ravichandran'
	}
};

export const t = 4;
export const QuestionContext = createContext();

const SurveySection = (props) => {
	const [isLoading, setIsLoading] = useState(false);
	const [isOpen, setOpen] = React.useState(false);
	const [values, setValues] = React.useState({
		title: '',
		descrpition: ''
	});

	const [count, setCount] = React.useState(0);
	const [newQuestion, addNew] = React.useState([]);

	const [isShow, setShow] = useState(false);

	//	console.log(props);
	const surveyId = props.match.params.surveyId;

	const [sectionIndex, setSectionIndex] = React.useState();

	const [data, setData] = useState({ hits: [] });
	const [surveySection, setSurveySection] = useState({ hits: [] });

	const handleChange = (prop) => (event) => {
		setValues({ ...values, [prop]: event.target.value });
	};

	const handleShow = () => {
		setShow((prev) => !prev);
	};

	function handleView(e, index) {
		console.log(e.target, index.index);
		setOpen((prev) => !prev);
		setSectionIndex(index.index);
		//	QuestionsDisplay(index.index);
	}

	useEffect(() => {
		const fetchData = async () => {
			setIsLoading(true);
			const result = await getSurveyById(surveyId);
			console.log(result);
			setData(result);
			setSurveySection(result.surveySections);
			setIsLoading(false);
			//	console.log(data);
			//	console.log(isLoading);
		};
		fetchData();
	}, []);

	//	console.log("Survvey Section Log:");
	//console.log(data);

	const QuestionsDisplay = () => {
		console.log(data);
		console.log(surveySection[sectionIndex]);
		if (typeof sectionIndex !== 'undefined') {
			if (surveySection[sectionIndex].questions.length > 0)
				return <QuestionDetails data={surveySection[sectionIndex]} />;
			else {
				alert("There is no question in this section, do you want to create new quesitons now?");
			}
		}
		else return <div />;
	}

	return (
		<div>
			<Grid>
				<Grid item lg={3} md={6} xs={12}>
					<Button color="secondary" fullWidth variant="contained" onClick={handleShow}>
						Edit
					</Button>
				</Grid>
				{typeof surveySection.length == 'undefined' || surveySection.length == 0 ? surveySection.length == 0 ? (
					<div>
						<NewSectionComp data={surveyId} id={surveyId} />
					</div>
				) : (
						<div>Loading</div>
					) : (
						surveySection.map((item, index) => (
							<div key={index}>
								<Box p={2} >
									<Card>
										<CardHeader
											action={
												<div>
													<IconButton
														color="secondary"
														aria-label="add an alarm"
														onClick={() => {
															alert("Switch to Edit Board");
														}}
													>
														<EditIcon color="primary" fontSize="large" />
													</IconButton>

													<Button
														color="secondary"
														variant="contained"
														onClick={(e) => handleView(e, { index })}
													>
														View
												</Button>
												</div>
											}
											//   subheader="Description"
											title={item.sectionTitle}
										/>
										<Divider />
										<CardContent>
											<Box display="flex" p={1}>
												<Typography variant="subtitle1" gutterBottom>
													{item.sectionIntroduction}
												</Typography>
											</Box>
										</CardContent>
									</Card>
									<Collapse in={isOpen}>
										<Box>
											{QuestionsDisplay}
										</Box>
									</Collapse>
								</Box>
							</div>
						))
					)}
				{newQuestion.map((nq) => {
					console.log(nq);
					return (
						<div key={nq}>
							<Box p={1} />
							<NewSectionComp data={data} id={surveyId} />
						</div>
					);
				})}
				<Collapse in={isShow}>
					<Box p={1}>
						<Button
							color="primary"
							fullWidth
							variant="contained"
							onClick={() => {
								setCount(count + 1);
								addNew([...newQuestion, count]);
							}}
						>
							Add New Section22
						</Button>
					</Box>
				</Collapse>
			</Grid>
		</div>
	);
};

export default SurveyQuestions;
