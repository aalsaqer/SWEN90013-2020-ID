//TODO: clean this up... has 'test' code in here
import React, { Component } from "react";
import { render } from "react-dom";
import { SortableContainer, SortableElement } from "react-sortable-hoc";
import arrayMove from "array-move";
import DragIndicatorIcon from "@material-ui/icons/DragIndicator";
import PrimaryButton from "./../reusableComponents/PrimaryButton";

const SortableItem = SortableElement(({ value }) => (
  <li
    style={{
      margin: 5,
      color: "white",
      background:
        "linear-gradient(40deg, rgb(255, 110, 196), rgb(120, 115, 245))",
      borderRadius: "2rem",
      listStyle: "none",
      textAlign: "center",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      padding: "8px 20px",
    }}
  >
    {/* <PrimaryButton style={{ display: "flex", alignItems: "center" }}> */}
    <DragIndicatorIcon />
    {value}
    {/* </PrimaryButton> */}
  </li>
));

const SortableList = SortableContainer(({ items }) => {
  return (
    <ul style={{ width: "100%", padding: 0 }}>
      {items.map((value, index) => (
        <SortableItem key={`item-${value}`} index={index} value={value} />
      ))}
    </ul>
  );
});

class SortableComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      items: this.props.options,
    };
  }

  onSortEnd = ({ oldIndex, newIndex }) => {
    this.setState(({ items }) => ({
      items: arrayMove(items, oldIndex, newIndex),
    }));
  };
  render() {
    return <SortableList items={this.state.items} onSortEnd={this.onSortEnd} />;
  }
}

export default SortableComponent;
