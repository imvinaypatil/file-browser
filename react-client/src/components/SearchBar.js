import React from 'react';
import {Input} from 'antd';

const Search = Input.Search;

export default class SearchBar extends React.Component {

    render() {
        return (
            <Search placeholder="Search ..." enterButton />
        );
    }
}
