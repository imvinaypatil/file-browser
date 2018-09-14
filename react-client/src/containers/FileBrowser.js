import React, { Component } from 'react';
import {Page, Card, ResourceList, TextStyle} from '@shopify/polaris';
import "antd/dist/antd.css";
import { message, List, Avatar, Icon, Spin} from "antd";
import WindowScroller from 'react-virtualized/dist/commonjs/WindowScroller';
import AutoSizer from 'react-virtualized/dist/commonjs/AutoSizer';
import VList from 'react-virtualized/dist/commonjs/List';
import InfiniteLoader from 'react-virtualized/dist/commonjs/InfiniteLoader';
import axios from 'axios';
import '../styles/FileBrowser.css';
import '../components/SearchBar';
import SearchBar from '../components/SearchBar';
import InfiniteScroll from 'react-infinite-scroller';
import FileUploader from '../components/FileUploader';

import MusicIcon from '../resources/img/icons8-music-48.png'

export default class FileBrowser extends Component {

  state = {
    data: [],
    loading: false,
    hasMore: true,
    currentDirectory: '/root',
  }

  loadedRowMap = {}

  getData = (path) => {
    return axios.get('/files/vin/list',{
      params: {
        "path": path
      }
    })
  };
  
  getDataAndSet = (path) => {
    this.getData(path).then(res => {
      this.setState({data: res.data});
    }).catch(err => console.error(err));
  }

  componentDidMount() {
    this.getData("/root").then(res => {
      this.setState({
        data: res.data
      })
    }).catch(err => console.error(err));
  }

  handleInfiniteOnLoad = ({startIndex, stopIndex}) => {
    let data = this.state.data;
    this.setState({
      loading: true,
    });
    // for (let i=startIndex;i<=stopIndex;i++) {
    //   this.loadedRowMap[i] = 1 // 1 means loading..
    // }
    if (data.length > 14) {
      message.warning('Loaded all');
      this.setState({
        loading: false,
        hasMore: false,
      });
      return;
    }
    this.getData().then(res => {
      data = data.concat(res.data);
      this.setState({
        data,
        loading: false,
      }).catch(err => console.error(err));
    });
  }

  isRowLoaded = ({index}) => {
    return !!this.loadedRowMap[index];
  }

  parseDate = (date) => {
    return new Date(date).toLocaleString();
  }

  sortDataByType = (a,b) => {
    if(b.type === 'directory') return 1;
    else if (b.type === 'file') return -1;
    else return 0;
  }

  renderItem = ({index,key,style}) => {
    let {data} = this.state;
    data = data.sort(this.sortDataByType);
    const item = data[index];
    return (
      <List.Item key={key} style={style}>
        <List.Item.Meta
          avatar={<Avatar src={MusicIcon} />}
          title={<a href={() => this.getDataAndSet('/root/dir')}>{item.name}</a>}
          description={this.parseDate(item.creationTime)} />
          <div>{item.type === "file" ? <Icon type="file" theme="outlined" /> : <Icon type="folder" theme="outlined" />}</div>
          
      </List.Item>
    );
  }

    render () {
      let {data} = this.state;
      data = data.sort(this.sortDataByType);
      const vlist = ({height,isScrolling,onChildScroll,scrollTop,onRowRendered,width}) => (
        <VList
          autoHeight
          height={height}
          isScrolling={isScrolling}
          onScroll={onChildScroll}
          overscanRowCount={2}
          rowCount={data.length}
          rowHeight={73}
          rowRenderer={this.renderItem}
          // onRowsRendered={onRowsRendered}
          scrollTop={scrollTop}
          width={width}
        />
      );
      const autoSize = ({ height, isScrolling, onChildScroll, scrollTop, onRowsRendered }) => (
        <AutoSizer disableHeight>
          {({ width }) => vlist({ height, isScrolling, onChildScroll, scrollTop, onRowsRendered, width })}
        </AutoSizer>
      );
      const infiniteLoader = ({ height, isScrolling, onChildScroll, scrollTop }) => (
        <InfiniteLoader
          isRowLoaded={this.isRowLoaded}
          loadMoreRows={this.handleInfiniteOnLoad}
          rowCount={data.length}
        >
          {({ onRowsRendered }) => autoSize({ height, isScrolling, onChildScroll, scrollTop, onRowsRendered })}
        </InfiniteLoader>
      );

        return (
        <Page>   
          <SearchBar/>    
          <div className="infinite-container">
            <InfiniteScroll 
              initialLoad={false}
              pageStart={0}
              loadMore={this.handleInfiniteOnLoad}
              hasMore={!this.state.loading && this.state.hasMore}
              useWindow={false}>

              <List
                dataSource={this.state.data}
                renderItem={item => (
                  <List.Item key={item.id} actions={
                    [<Icon type="unlock" theme="twoTone" />,
                    <Icon type="edit" theme="twoTone" />,
                    <Icon type="delete" theme="twoTone" />]}>
                    <List.Item.Meta
                      avatar={<Avatar  />}
                      title={<a href={() => this.getDataAndSet('/root/dir')}>{item.name}</a>}
                      description={this.parseDate(item.creationTime)} />
                      <div>{item.type === "file" ? <Icon type="file" style={{ fontSize: '25px', color: '#5c7080' }} theme="filled" /> : <Icon type="folder" style={{ fontSize: '25px', color: '#5c7080' }} theme="outlined" />}</div>
                    </List.Item>
                )} >
                {this.state.loading && this.state.hasMore && (
                  <div className="loading-container">
                   <Spin />
                 </div>
              )}
              </List>
            </InfiniteScroll>
          </div>   
          
          <FileUploader></FileUploader>
        </Page>
        );
    }
}
