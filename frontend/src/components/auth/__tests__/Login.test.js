import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import { store } from '../../../store/store';
import Login from '../Login';

describe('Login', () => {
  it('renders the login form', () => {
    render(
      <Provider store={store}>
        <Router>
          <Login />
        </Router>
      </Provider>
    );
    expect(screen.getByLabelText(/username/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/password/i)).toBeInTheDocument();
  });
});
