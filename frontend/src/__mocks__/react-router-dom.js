import React from 'react';

export const BrowserRouter = ({ children }) => <div>{children}</div>;
export const Link = ({ children }) => <a>{children}</a>;
export const useNavigate = () => jest.fn();
