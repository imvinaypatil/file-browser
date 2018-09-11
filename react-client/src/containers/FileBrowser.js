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
          const {id, url, name,type, location} = item;
          const media = <Avatar size="medium" name={name} />;

          return (
            <ResourceList.Item id={id} url={url} media={media}>
              <h3>
                <TextStyle variation="strong">{name}</TextStyle><br />
                <TextStyle variation="strong">{type}</TextStyle>
              </h3>
              <div>{location}</div>
            </ResourceList.Item>
          );
        }}
      />          </Card>
        </Page>
        );
    }
}
