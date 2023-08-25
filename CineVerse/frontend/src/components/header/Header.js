import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faVideoSlash } from '@fortawesome/free-solid-svg-icons';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { NavLink, useNavigate } from 'react-router-dom';

const Header = () => {
  const navigate = useNavigate();

  const navigateToLogin = (e) => {
    e.preventDefault();
    navigate('/login');
  };

  const navigateToRegister = (e) => {
    e.preventDefault();
    navigate('/register');
  };

  const handleLogout = (e) => {
    localStorage.removeItem('token');
    navigate('/');
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container fluid>
        <Navbar.Brand href="/" style={{ color: 'gold' }}>
          <FontAwesomeIcon icon={faVideoSlash} />
          Gold
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav className="me-auto my-2 my-lg-0" style={{ maxHeight: '100px' }} navbarScroll>
            <NavLink className="nav-link" to="/">
              Home
            </NavLink>
            <NavLink className="nav-link" to="/watchList">
              Watch List
            </NavLink>
          </Nav>
          {localStorage.getItem('token') != null ? (
            <Button onClick={handleLogout} variant="outline-info" className="me-2">
              Logout
            </Button>
          ) : (
            <>
              <Button onClick={navigateToLogin} variant="outline-info" className="me-2">
                Login
              </Button>
              <Button onClick={navigateToRegister} variant="outline-info">
                Register
              </Button>
            </>
          )}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
