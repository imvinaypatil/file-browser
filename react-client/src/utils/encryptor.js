'use strict';

let workerjs=' \
  onmessage=function(evt) { \
		var encrypt=crypto.workersubtle.encrypt({name:"AES-CBC",iv:new Uint8Array(16)},evt.data[0]); \
		var buffer=evt.data[1]; \
		var block=XXX; \
		while (buffer.length) { \
			encrypt.process(buffer.subarray(0,block)).done(function(result) { \
				postMessage(result) \
			}); \
			buffer=buffer.subarray(Math.min(buffer.length,block)); \
		}; \
		encrypt.finish().done(function(result) { \
			postMessage([result]); \
		}); \
	} \
';

var process_upload=function() {
	var file=this.files[0];
	var process=function(key) {
		var reader=new FileReader();
		var readed=function() {
			var request={};
			var file_enc=[];
			var size=reader.result.byteLength;
			var tsize=0;
			var worker=new Worker(URL.createObjectURL(new Blob([workerjs])));
			var h=crypto.subtle.digest('SHA-256'); 
			worker.onmessage=function(evt) {
				var res=(evt.data instanceof Array)?evt.data[0]:evt.data;
				tsize +=res.length;//display a progress bar (tsize/size)*100%
				file_enc.push(res);
				h.process(res).then(function() {
					if (evt.data instanceof Array) { //end
						request.blob=new Blob(file_enc,{type:'application/octet-binary'});
						request.type=file.type;
						this.finish(res).done(function(result) {
							request.file_hash=result;
							store_DB(request);
						});
					};
				});
			};
			worker.postMessage([key,new Uint8Array(reader.result)]);
		};
		reader.addEventListener("loadend",readed,false);
		reader.readAsArrayBuffer(file);
	};
	crypto.importKey("raw",TextToArrayBufferView('00112233445566778899aabbccddeeff'),'AES-CBC',false,['encrypt','decrypt']).done(function(result) {process(result)});
};