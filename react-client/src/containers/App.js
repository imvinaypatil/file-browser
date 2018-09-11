import React, { Component } from 'react';
// import logo from './logo.svg';
import '../styles/App.css';
import '@shopify/polaris/styles.css';
import "@blueprintjs/core/lib/css/blueprint.css";
import "@blueprintjs/icons/lib/css/blueprint-icons.css";
import MyNavbar from '../components/MyNavbar';
import FileBrowser from './FileBrowser';
import axios from 'axios';

class App extends Component {

  constructor() {
    super();
    this.state = {
      filelist: []
    }
  };

  componentDidMount() {
    axios.post('/files/user/list',{path: "/root"})
      .then(response => {
        this.setState({filelist: response});
      }).catch( err => console.log(err));
  };

  callListApi() {
    async () => {
      const response = await fetch('/api/hello');
      const body = await response.json();
      if (response.status !== 200) throw Error(body.message);
      return body;
    }
  };

  render() {
    return (
      <div className="App">
        <MyNavbar></MyNavbar>
        <FileBrowser list={this.state.filelist}></FileBrowser>
      </div>
    );
  }
}

export default App;
