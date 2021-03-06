//TODO: refactor so that sidebar is a child of navbar, not vice versa...
import React, { useEffect, useState } from "react";
import clsx from "clsx";
import {
  BrowserRouter as Router,
  Route,
  Link,
  Switch,
  NavLink,
} from "react-router-dom";
import { makeStyles, useTheme } from "@material-ui/core/styles";
import Drawer from "@material-ui/core/Drawer";
// import CssBaseline from "@material-ui/core/CssBaseline";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import List from "@material-ui/core/List";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import Grid from "@material-ui/core/Grid";
import MenuIcon from "@material-ui/icons/Menu";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import ChevronRightIcon from "@material-ui/icons/ChevronRight";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import Collapse from "@material-ui/core/Collapse";
import MenuList from "@material-ui/core/MenuList";
import MenuItem from "@material-ui/core/MenuItem";
import StorageIcon from "@material-ui/icons/Storage";
import ExpandLess from "@material-ui/icons/ExpandLess";
import ExpandMore from "@material-ui/icons/ExpandMore";
import BallotIcon from "@material-ui/icons/Ballot";
import IconLogo from "../images/idecide-logo.png";
import withWidth, { isWidthUp } from "@material-ui/core/withWidth";
import { getAllSurveys, getSectionBySurveyId } from "../API/surveyAPI";
import AssignmentIcon from "@material-ui/icons/Assignment";
import PhoneIcon from "@material-ui/icons/Phone";
import AccountCircle from "@material-ui/icons/AccountCircle";
import { Box, Tooltip } from "@material-ui/core";
import ExitToAppIcon from "@material-ui/icons/ExitToApp";
import { createMuiTheme } from "@material-ui/core/styles";
import purple from "@material-ui/core/colors/purple";
import { getCsvDownloadLink } from "../API/surveyResultsAPI";

import userContext from "../contexts/userContext";
import PrimaryButton from "./../components/reusableComponents/PrimaryButton";
import GetHelpDialog from "../components/reusableComponents/getHelpDialog";
import { useHistory } from "react-router-dom";
import keyValuePairsInfo from "./keyValuePagesInfo.json";
import { EditIcon } from "@material-ui/icons/Edit";
import Loading from "./../components/reusableComponents/loading";

const drawerWidth = 240;

const theme = createMuiTheme({
  palette: {
    primary: {
      main: purple[500],
    },
    secondary: {
      main: "#f44336",
    },
  },
});

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
    height: "100vh",
  },
  appBar: {
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    zIndex: 100,
  },
  appBarShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: theme.spacing(0),
    width: 50,
  },
  hide: {
    display: "none",
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerHeader: {
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: "flex-end",
  },
  button: {
    width: 170,
    [theme.breakpoints.only("xs")]: {
      width: 20,
    },
  },
  content: {
    flexGrow: 1,
    transition: theme.transitions.create("margin", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: -drawerWidth,
    marginTop: "60px",
  },
  contentShift: {
    transition: theme.transitions.create("margin", {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
    marginLeft: 2,
    marginTop: "60px",
  },
  nested: {
    paddingLeft: theme.spacing(4),
  },
}));

function NavBar(props) {
  let history = useHistory();
  const classes = useStyles();
  const theme = useTheme();
  const [isLoading, setIsLoading] = useState(false);
  const { width } = props;
  const [open, setOpen] = React.useState(false);
  const [openHelp, setHelp] = React.useState(false);
  const [surveys, setSurveys] = React.useState([]);
  // const [showSiteComponents, setshowSiteComponents] = React.useState(true);
  const [link, setLink] = React.useState();
  const [siteEditOpen, setSiteEditOpen] = React.useState(false);

  const [openDownload, setOpenDownload] = React.useState(false);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      const result = await getAllSurveys();

      setIsLoading(false);

      setSurveys(result.data);
    };

    fetchData();
  }, []);

  const handleDownloadOpen = () => {
    setOpenDownload(true);
    //   setIsLoading(true);
  };

  const handleDownload = async () => {
    const link = await getCsvDownloadLink()
      .then((response) => {
        if (response.data.code && response.data.code == 200) {
          const oa = document.createElement("a");
          oa.href = `//${link.data.data}`;
          oa.setAttribute("target", "_blank");
          document.body.appendChild(oa);
          oa.click();
        } else {
          alert(response.data.message);
          window.location.href = "./";
        }
      })
      .catch((error) => {
        alert(error);
      });
  };

  const handleDowloadClose = () => {
    setOpenDownload(false);
  };

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    setHelp(true);
  };
  const handleClose = () => {
    setHelp(false);
  };

  const toggleSiteEditOpen = () => {
    setSiteEditOpen(!siteEditOpen);
  };

  return (
    <userContext.Consumer>
      {(userContextValue) => (
        <div>
          {!isLoading && surveys ? (
            <div className={classes.root}>
              {/* <CssBaseline /> */}
              <AppBar
                position="fixed"
                style={{ background: "white" }}
                className={clsx(classes.appBar, {
                  [classes.appBarShift]: open,
                })}
              >
                <Toolbar>
                  <Collapse
                    in={userContextValue.userContext.userType === "admin"}
                  >
                    <Tooltip title="Dashboard Side Menu.">
                      <IconButton
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        className={clsx(
                          classes.menuButton,
                          open && classes.hide
                        )}
                      >
                        <MenuIcon />
                      </IconButton>
                    </Tooltip>
                  </Collapse>

                  {/* TODO: replace this with react-router SPA routing to avoid reloading pages */}
                  <IconButton
                    onClick={() => {
                      history.push("/");
                    }}
                  >
                    <img
                      src={IconLogo}
                      alt="IconLogo"
                      style={{ height: 35, marginTop: 0 }}
                    />
                  </IconButton>
                  <Grid
                    container
                    direction="row"
                    justify="flex-end"
                    alignItems="center"
                    spacing={2}
                  >
                    {userContextValue.userContext.userType &&
                      userContextValue.userContext.userType === "admin" && (
                        <Grid
                          container
                          direction="row"
                          justify="flex-end"
                          alignItems="center"
                          spacing={2}
                        >
                          <Grid item>
                            <PrimaryButton
                              onClick={userContextValue.logout}
                            // className={clsx(classes.button)}
                            >
                              <ExitToAppIcon />
                              {isWidthUp("sm", width) ? "LOGOUT" : ""}
                            </PrimaryButton>
                          </Grid>
                        </Grid>
                      )}
                    {(!userContextValue.userContext.userType ||
                      userContextValue.userContext.userType !== "admin") && (
                        <Grid
                          container
                          direction="row"
                          justify="flex-end"
                          alignItems="center"
                          spacing={2}
                        >
                          <Grid item>
                            <Tooltip title="Get more help">
                              <PrimaryButton
                                className={clsx(classes.button)}
                                onClick={handleOpen}
                              >
                                <PhoneIcon />
                                {isWidthUp("sm", width) ? "Get Help" : ""}
                              </PrimaryButton>
                            </Tooltip>
                          </Grid>
                          <Grid item>
                            <Tooltip title="Click to quickly exit the site and clear saved data">
                              <PrimaryButton
                                className={clsx(classes.button)}
                                onClick={() => {
                                  userContextValue.logout();
                                  window.location.href =
                                    "https://www.weather.com.au/";
                                }}
                              >
                                <ExitToAppIcon />
                                {isWidthUp("sm", width) ? "Quick Exit" : ""}
                              </PrimaryButton>
                            </Tooltip>
                          </Grid>
                        </Grid>
                      )}
                  </Grid>
                </Toolbar>
              </AppBar>
              <Drawer
                className={classes.drawer}
                variant="persistent"
                anchor="left"
                open={open}
                classes={{
                  paper: classes.drawerPaper,
                }}
              >
                <div className={classes.drawerHeader}>
                  <IconButton onClick={handleDrawerClose} style={{ width: 50 }}>
                    {theme.direction === "ltr" ? (
                      <ChevronLeftIcon />
                    ) : (
                        <ChevronRightIcon />
                      )}
                  </IconButton>
                </div>
                <Divider />

                <Box p={3}>
                  <Link to={"/dashboard/surveys"}>
                    <Typography variant="h6" gutterBottom>
                      DashBoard
                    </Typography>
                  </Link>
                  <Box p={1} />
                  <AccountCircle />
                  Admin
                </Box>

                <Divider />
                <List>
                  <ListItem
                    button
                    onClick={() => {
                      history.push("/");
                      history.push("/dashboard/surveys");
                    }}
                  >
                    <ListItemIcon>
                      <AssignmentIcon />
                    </ListItemIcon>
                    <Typography
                      color="textPrimary"
                      gutterBottom
                      variant="body1"
                    >
                      Surveys
                    </Typography>
                    <ListItemText />
                    {/* {showSiteComponents ? <ExpandLess /> : <ExpandMore />} */}
                  </ListItem>

                  {/* <Collapse in={showSiteComponents} timeout="auto" unmountOnExit>
                    {surveys &&
                      surveys.map((survey, index) => (
                        <MenuItem
                          className={classes.nested}
                          onClick={() => {
                            history.push(
                              "/dashboard/surveyId=" + survey.surveyId
                            );
                          }}
                        >
                          <ListItemIcon>
                            <AssignmentIcon />
                          </ListItemIcon>
                          <Typography
                            color="textSecondary"
                            gutterBottom
                            variant="body1"
                          >
                            {survey.surveyTitle}
                          </Typography>
                        </MenuItem>
                      ))}
                  </Collapse> */}

                  <ListItem button onClick={toggleSiteEditOpen}>
                    <ListItemIcon>
                      <BallotIcon />
                    </ListItemIcon>
                    <Typography
                      color="textPrimary"
                      gutterBottom
                      variant="body1"
                    >
                      Edit Site Components
                    </Typography>
                    <ListItemText />
                    {siteEditOpen ? <ExpandLess /> : <ExpandMore />}
                  </ListItem>
                  <Collapse in={siteEditOpen} timeout="auto" unmountOnExit>
                    {Object.keys(keyValuePairsInfo).map(
                      (keyValuePairName, index) => (
                        <NavLink
                          to={"/dashboard/kvEditing=" + keyValuePairName}
                          key={keyValuePairName}
                        >
                          <MenuItem className={classes.nested}>
                            <ListItemIcon>
                              <AssignmentIcon />
                            </ListItemIcon>
                            <Typography
                              color="textSecondary"
                              gutterBottom
                              variant="body1"
                            >
                              {keyValuePairsInfo[keyValuePairName].titleText}
                            </Typography>
                          </MenuItem>
                        </NavLink>
                      )
                    )}
                  </Collapse>

                  <ListItem button onClick={handleDownloadOpen}>
                    <ListItemIcon>
                      <StorageIcon />
                    </ListItemIcon>
                    <Typography
                      color="textPrimary"
                      gutterBottom
                      variant="body1"
                    >
                      Download Data
                    </Typography>
                  </ListItem>
                  <ListItem
                    button
                    onClick={() => {
                      history.push("/dashboard/images");
                    }}
                  >
                    <ListItemIcon>
                      <AssignmentIcon />
                    </ListItemIcon>
                    <Typography
                      color="textPrimary"
                      gutterBottom
                      variant="body1"
                    >
                      Images
                    </Typography>
                    <ListItemText />
                    {/* {showSiteComponents ? <ExpandLess /> : <ExpandMore />} */}
                  </ListItem>
                </List>
              </Drawer>

              {/* render main content window via renderprops */}
              <main
                className={clsx(classes.content, {
                  [classes.contentShift]: open,
                })}
              >
                {props.children}
              </main>

              <GetHelpDialog open={openHelp} handleClose={handleClose} />

              {/** dialog for download data*/}
              <Dialog
                open={openDownload}
                onClose={handleDowloadClose}
                aria-labelledby="max-width-dialog-title"
                maxWidth="md"
                fullWidth
              >
                <DialogTitle id="form-dialog-title">Download data</DialogTitle>
                <Divider />
                <DialogContent>
                  <DialogContentText>
                    Click the download button to download .csv file. {link}
                  </DialogContentText>
                </DialogContent>

                <DialogActions>
                  <PrimaryButton
                    onClick={handleDowloadClose}
                    className={clsx(classes.button)}
                  >
                    Cancel
                  </PrimaryButton>
                  <PrimaryButton
                    className={clsx(classes.button)}
                    onClick={handleDownload}
                  >
                    download
                  </PrimaryButton>
                </DialogActions>
              </Dialog>
            </div>
          ) : (
              <Loading isLoading={isLoading} />
            )}
        </div>
      )}
    </userContext.Consumer>
  );
}

export default withWidth()(NavBar);
