import React, {Component} from "react";
import ReactDropzone from "react-dropzone";
import axios from 'axios';
import cryptojs from 'crypto-browserify';

import '../styles/FileUploader.css';

const passphrase = '12345';
const iv = 1324;


export default class FileUploader extends Component {
    onDrop = async (files) => {
        const formData = new FormData();
        let file = files[0];
        let encrypted;
        const reader = new FileReader();
        let pwUtf8 = new TextEncoder().encode(passphrase);
        const pwHash = await crypto.subtle.digest('SHA-256', pwUtf8);
        const alg = { name: 'AES-GCM', iv: iv };
        const key = await crypto.subtle.importKey('raw', pwHash, alg, false, ['encrypt']);
        
        encrypted = await crypto.subtle.encrypt(alg,key,buffer)

        files.map(file => {
            let reader = new FileReader();
            let encrypted;
            const alg = { name: 'AES-GCM', iv: iv };
            crypto.subtle.encrypt()           reader.onload = () => {
                encrypted = crypto.AES.encrypt(reader.result, '12345');
            }
            reader.readAsDataURL(file); 
            return encrypted;
        }).forEach(file => formData.append('file',file));
        const config = {
            params: {
                parent: '/root'
            }
        }
        axios.post('/files/vin/upload',formData,config).then(res => {
            console.log(res.data)
        }).catch(err => console.error(err));
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