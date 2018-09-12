import React, { Component } from 'react';
import {Page, Card, ResourceList, Avatar, TextStyle} from '@shopify/polaris';

export default class FileBrowser extends Component {

    render () {
        return (
        <Page>
          <Card sectioned>
          <ResourceList
            showHeader
            items= {this.props.list}
            renderItem={(item) => {
              const {id, url, name, type, parentDirectory, creationTime, location} = item;
              const media = <Avatar customer size="medium" name={type} />;
              const date = new Date(creationTime).toLocaleString();
              return (
                <ResourceList.Item id={id} url={url} media={media} onClick={this.props.callListApi(parentDirectory+"/"+name)}>
                  <h3>
                    <TextStyle variation="strong">{name}</TextStyle><br />
                    <TextStyle>{date}</TextStyle>
                  </h3>
                  {/* <div>{parentDirectory}</div> */}
                </ResourceList.Item>
              );
            }}
          />          
        </Card>
        </Page>
        );
    }
}
