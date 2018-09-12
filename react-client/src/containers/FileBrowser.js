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

              return (
                <ResourceList.Item id={id} url={url} media={media} onClick={this.props.}>
                  <h3>
                    <TextStyle variation="strong">{name}</TextStyle><br />
                    <TextStyle>{creationTime}</TextStyle>
                  </h3>
                  <div>{location}</div>
                </ResourceList.Item>
              );
            }}
          />          
        </Card>
        </Page>
        );
    }
}
