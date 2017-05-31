import React from 'react'
import { render } from 'react-dom'
import Root from './containers/Root'
import registerServiceWorker from './registerServiceWorker'
import './index.css'

render(
  <Root />,
  document.getElementById('root')
)

registerServiceWorker();
