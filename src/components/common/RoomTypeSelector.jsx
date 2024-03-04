import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import { getRoomTypes } from "../utils/ApiFunctions";

const RoomTypeSelector = ({ handleRoomInputChange, newRoom }) => {
  const [roomTypes, setRoomTypes] = useState([""]);
  const [showNewRoomTypeInput, setShowNewRoomTypeInput] = useState(false);
  const [newRoomType, setNewRoomType] = useState("");

  useEffect(() => {
    fetchRoomTypes();
  }, []);

  const fetchRoomTypes = async () => {
    try {
      const types = await getRoomTypes();
      setRoomTypes(types);
    } catch (error) {
      console.error("Error fetching room types:", error.message);
    }
  };

  const handleNewRoomTypeInputChange = (e) => {
    setNewRoomType(e.target.value);
  };

  const handleAddNewRoomType = () => {
    if (newRoomType !== "") {
      setRoomTypes([...roomTypes, newRoomType]);
      setNewRoomType("");
      setShowNewRoomTypeInput(false);
    }
  };

  return (
    <>
      {roomTypes && roomTypes.length > 0 && (
        <div>
          <select
            required
            className="form-select"
            name="roomType"
            onChange={(e) => {
              if (e.target.value === "Add New") {
                setShowNewRoomTypeInput(true);
              } else {
                handleRoomInputChange(e);
              }
            }}
            value={newRoom.roomType}
          >
            <option value={""}> select a room type</option>
            <option value={"Add New"}>Add New</option>
            {roomTypes.map((type, index) => (
              <option key={index} value={type}>
                {type}
              </option>
            ))}
          </select>
          {showNewRoomTypeInput && (
            <div className="input-group">
              <input
                className="form-control"
                type="text"
                placeholder="Enter a new room type"
                onChange={handleNewRoomTypeInputChange}
              />

              <button
                className="btn btn-hotel"
                type="button"
                onClick={handleAddNewRoomType}
              >
                Add
              </button>
            </div>
          )}
        </div>
      )}
    </>
  );
};

RoomTypeSelector.propTypes = {
  handleRoomInputChange: PropTypes.func.isRequired,
  newRoom: PropTypes.object.isRequired,
};

export default RoomTypeSelector;
