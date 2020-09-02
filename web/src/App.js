import React from 'react'
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom'
import GestaoPessoasScreen from './screens/GestaoPessoasScreen'

import './App.css'

const routes = [

  {
    path: '/gestao',
    component: GestaoPessoasScreen
  }
];

function App() {
  return (
    <Router basename="admin">
      <div className='App'>
        <Switch >
          {routes.map((route) => (
            <Route
              exact
              key={route.path}
              path={route.path}
              component={route.component}
            />
          ))}
        </Switch>
      </div>
    </Router>
  );
}

export default App;
