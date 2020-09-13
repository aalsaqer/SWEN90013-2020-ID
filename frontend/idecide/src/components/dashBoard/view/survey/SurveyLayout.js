import React, { useState, useEffect, createContext } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import SurveyCard from './surveyView/SurveyCard';
import {
	Button,
	Dialog,
	DialogTitle,
	DialogContent,
	TextField,
	DialogContentText,
	DialogActions,
	Collapse,
	IconButton
} from '@material-ui/core';
import Alert from '@material-ui/lab/Alert';
import CloseIcon from '@material-ui/icons/Close';

import { getAllSurveys, AddNewSurvey } from '../../../../API/surveyAPI';

const useStyles = makeStyles((theme) => ({
	root: {
		flexGrow: 1
	},
	paper: {
		padding: theme.spacing(2),
		textAlign: 'center',
		color: theme.palette.text.secondary
	}
}));

export const CountContext = createContext();
console.log(CountContext);

export default function SurveyLayout() {
	const classes = useStyles();
	const [isLoading, setIsLoading] = useState(false);
	const [data, setData] = useState({ hits: [] });
	const [open, setDMOpen] = React.useState(false); //control of adding new survey
	//const [ isOpen, setOpen ] = React.useState(false);
	const [openAlert, setOpen] = React.useState(false);
	const [openGreen, setOpenGreen] = React.useState(false);
	const [error, setError] = React.useState();
	const [values, setValues] = React.useState({
		title: '',
		descrpition: ''
	});

	const handleChange = (prop) => (event) => {
		setValues({ ...values, [prop]: event.target.value });
	};

	const handleOpen = () => {
		setDMOpen(true);
	};

	const handleClose = () => {
		setDMOpen(false);
	};

	const AddNewSurveys = async () => {
		if (openGreen) {
			window.location.href = './survey';
		} else {
			//
			console.log(values.descrpition);
			//	console.log();
			var readyData = JSON.stringify({
				surveyId: Array.from(data).length + 1,
				surveyTitle: values.title,
				surveyIntroduction: values.descrpition,
				surveyVersion: '',
				surveySections: []
				//	surveySections:product.surveySections,
				//	jsonStr:null,
				//	surveyImageName: product.surveyImageName,
			});
			const feedBack = await AddNewSurvey(readyData)
				.then((data) => {
					setOpenGreen(true);
				})
				.catch((error) => {
					setOpen(true);
					setError(error + '');
					//			alert('Error from processDataAsycn() with async( When promise gets rejected ): ' + error);
				});
			return feedBack;
		}
	};

	useEffect(() => {
		const fetchData = async () => {
			setIsLoading(true);
			const result = await getAllSurveys();

			setData(result.data);
			setIsLoading(false);
			//		console.log(data);
			//		console.log(isLoading);
		};

		fetchData();
	}, []);


	console.log(Array.from(data).length);

	return (
		<div>
			{isLoading ? (
				<div>Loading ...</div>
			) : (
					<Grid>
						<Grid container spacing={10}>
							{Array.from(data).map((item) => (
								<Grid item lg={4} md={6} xs={12} key={item.surveyId}>
									<SurveyCard
										key={item.surveyId}
										product={item}
										useStyles
										onClick={() => {
											window.location.pathname = '/dashboard/surveyId=' + item.surveyId;
										}}
									/>
									<CountContext.Provider value={item.surveyId} />
								</Grid>
							))}
							<Grid item xs={12}>
								<Button variant="contained" color="secondary" fullWidth onClick={handleOpen}>
									Add New Survey
							</Button>
							</Grid>
						</Grid>

						<Dialog
							open={open}
							onClose={handleClose}
							aria-labelledby="max-width-dialog-title"
							//	fullWidth="md"
							maxWidth="md"
						>
							<DialogTitle id="form-dialog-title">Survey</DialogTitle>
							<DialogContent>
								<Collapse in={!openGreen}>
									<DialogContentText>
										Please input the title and description for the new Survey.
								</DialogContentText>
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
									<Alert severity="success">Update Survey Successfully!</Alert>
								</Collapse>
							</DialogContent>
							<DialogActions>
								<Collapse in={!openGreen}>
									<Button onClick={handleClose} color="primary">
										Cancel
								</Button>
								</Collapse>
								<Button onClick={AddNewSurveys} color="primary">
									Confirm
							</Button>
							</DialogActions>
						</Dialog>
					</Grid>
				)}
		</div>
	);
}