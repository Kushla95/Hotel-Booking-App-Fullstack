import "../node_modules/bootstrap/dist/css/bootstrap.css";
import "../node_modules/bootstrap/dist/js/bootstrap.bundle.js";
import "./App.css";
import AddRoom from "./components/room/AddRoom";
import ExistingRooms from "./components/room/ExistingRooms.jsx";

function App() {
  return (
    <>
      <AddRoom />
      <ExistingRooms />
    </>
  );
}

export default App;
