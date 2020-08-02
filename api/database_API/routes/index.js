var express = require('express');
var DetectionController = require('../Controller/detections');
var CriminalController = require('../Controller/criminals')
var ViolenceController = require('../Controller/violence')
const router = express.Router();

router.get('/api/detections', DetectionController.getAllDetections);
router.get('/api/detections/:id', DetectionController.getDetection);
router.get('/api/detection/:cid', DetectionController.getDetections);
router.post('/api/detections', DetectionController.createDetection);
router.put('/api/detections/:id', DetectionController.updateDetection);
router.delete('/api/detections/:id', DetectionController.deleteDetection);

router.get('/api/criminals', CriminalController.getAllCriminals);
router.get('/api/criminals/:id', CriminalController.getCriminal);

router.get('/api/violence', ViolenceController.getAllDetections);
router.get('/api/violence/:id', ViolenceController.getDetection);
router.post('/api/violence', ViolenceController.createDetection);
router.put('/api/violence/:id', ViolenceController.updateDetection);
router.delete('/api/violence/:id', ViolenceController.deleteDetection);


module.exports = router;

