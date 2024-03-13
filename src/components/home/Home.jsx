import HotelService from "../common/HotelService";
import Parallax from "../common/Parallax";
import RoomCarousel from "../common/RoomCarousel";
import MainHeader from "../layout/MainHeader";

const Home = () => {
  return (
    <section>
      <MainHeader />
      <div className="container">
        <RoomCarousel />
        <Parallax />
        <RoomCarousel />
        <HotelService />
        <Parallax />
        <RoomCarousel />
      </div>
    </section>
  );
};

export default Home;
