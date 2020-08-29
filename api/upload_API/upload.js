const express = require('express')
const bodyParser = require('body-parser');
const multer = require('multer')
const azureStorage = require('azure-storage');
const getStream = require('into-stream');
const inMemoryStorage = multer.memoryStorage();
const singleFileUpload = multer({ storage: inMemoryStorage });
var cors = require('cors')
const app = express()
const port = process.env.PORT || 6000

app.use(cors())

// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }));
 
// parse application/json
app.use(bodyParser.json());
 
const azureStorageConfig = {
    accountName: "accountname",
    accountKey: "accountkey",
    blobURL: "bloburl",
    containerName: "container"
};
 
uploadFileToBlob = async (directoryPath, file) => {
 
    return new Promise((resolve, reject) => {
 
        const blobName = /*getBlobName( */file.originalname/*)*/;
        const stream = getStream(file.buffer);
        const streamLength = file.buffer.length;
 
        const blobService = azureStorage.createBlobService(azureStorageConfig.accountName, azureStorageConfig.accountKey); 
        blobService.createBlockBlobFromStream(azureStorageConfig.containerName, `${directoryPath}/${blobName}`, stream, streamLength, err => {
            if (err) {
                reject(err);
            } else {
                resolve({ filename: blobName, 
                    originalname: file.originalname, 
                    size: streamLength, 
                    path: `${azureStorageConfig.containerName}/${directoryPath}/${blobName}`,
                    url: `${azureStorageConfig.blobURL}${azureStorageConfig.containerName}/${directoryPath}/${blobName}` });
            }
        });
 
    });
 
};
 
// const getBlobName = originalName => {
//     const identifier = Math.random().toString().replace(/0\./, ''); // remove "0." from start of string
//     return `${identifier}-${originalName}`;
// };
 
const imageUpload = async(req, res, next) => {
    try {
        const image = await uploadFileToBlob('images', req.file); // images is a directory in the Azure container
        return res.json(image);
    } 
    catch (error) {
        next(error);
    }
}

const videoUpload = async(req, res, next) => {
    try {
        const video = await uploadFileToBlob('tobedetected', req.file); // tobedetected is a directory in the Azure container
        return res.json(video);
    } 
    catch (error) {
        next(error);
    }
}

const violenceUpload = async(req, res, next) => {
    try {
        const video = await uploadFileToBlob('videos', req.file); // videos is a directory in the detections container
        return res.json(video);
    } 
    catch (error) {
        next(error);
    }
}

app.post('/upload/image', singleFileUpload.single('image'), imageUpload)
app.post('/upload/video', singleFileUpload.single('video'), videoUpload)
app.post('/upload/violence', singleFileUpload.single('video'), violenceUpload)

app.listen(port, () => console.log(`App listening on port ${port}!`))
