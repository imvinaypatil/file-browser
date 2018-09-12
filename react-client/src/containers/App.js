import React, { Component } from 'react';
// import logo from './logo.svg';
import '../styles/App.css';
import '@shopify/polaris/styles.css';
import "@blueprintjs/core/lib/css/blueprint.css";
import "@blueprintjs/icons/lib/css/blueprint-icons.css";
import MyNavbar from '../components/MyNavbar';
import FileUploader from '../components/FileUploader';

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
    this.callListApi('/root');
  };

  callListApi(path) {
    axios.get('/files/vin/list',{
      params: {
        "path": path
      }
    })
      .then(res => {
        this.setState({filelist: res.data});
      }).catch( err => console.log(err));
  };

  render() {
    return (
      <div className="App">
        <MyNavbar></MyNavbar>
        <FileBrowser list={this.state.filelist} callListApi={this.callListApi}></FileBrowser>
        <FileUploader></FileUploader>
      </div>
    );
  }
}

export default App;
