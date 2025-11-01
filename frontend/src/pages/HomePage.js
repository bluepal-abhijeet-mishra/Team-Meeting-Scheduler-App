import React from 'react';
import { Container, Row, Col, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div className="bg-dark text-white">
      <Container className="text-center py-5">
        <h1 className="display-4">Welcome to Team Meeting Scheduler</h1>
        <p className="lead">
          The easiest way to schedule and manage your team meetings.
        </p>
        <Row className="justify-content-center">
          <Col md={6}>
            <p>
              Our application provides a seamless experience for scheduling
              meetings, inviting participants, and sending reminders. With our
              intuitive calendar interface, you can easily view and manage your
              team's schedule.
            </p>
            <div>
              <Link to="/login">
                <Button variant="primary" className="m-2">
                  Login
                </Button>
              </Link>
              <Link to="/register">
                <Button variant="secondary" className="m-2">
                  Register
                </Button>
              </Link>
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default HomePage;
