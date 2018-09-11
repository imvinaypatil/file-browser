import React from 'react';
import ReactDOM from 'react-dom';
import './styles/index.css';
import App from './containers/App';
import '@shopify/polaris';
import {AppProvider} from '@shopify/polaris';

import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(
    <AppProvider>
        <App />
    </AppProvider>, document.getElementById('root'));
registerServiceWorker();
