import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import { store } from '../../../store/store';
import Header from '../Header';

describe('Header', () => {
  it('renders login and register buttons when no user is logged in', () => {
    render(
      <Provider store={store}>
        <Router>
          <Header />
        </Router>
      </Provider>
    );
    expect(screen.getByText(/login/i)).toBeInTheDocument();
    expect(screen.getByText(/register/i)).toBeInTheDocument();
  });
});
