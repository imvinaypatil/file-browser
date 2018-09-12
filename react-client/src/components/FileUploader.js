import React, {Component} from "react";
import ReactDropzone from "react-dropzone";
import axios from 'axios';
import '../styles/FileUploader.css';

export default class FileUploader extends Component {
    onDrop = (files) => {
        const formData = new FormData();
        files.forEach(file => formData.append('file',file));
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            },
            param: {
                'parent': '/root'
            }
        }
        axios.post('/files/vin/upload',formData,config);
    }

    render() {
        return (
            <div>
                <ReactDropzone className="upload-box" onDrop={this.onDrop}>
                    Drop your files here to upload.
                </ReactDropzone>
            </div>
        );
    }
}