/* eslint-disable class-methods-use-this */
const pool = require('../connect');

class DetectionsController{
// get all detections
getAllDetections(req, res) {
  pool.query("SELECT * FROM public.detections ORDER BY time_stamp DESC", (error, results) => {
    if(error) {
      console.log(error);
    } 
    else {
    if(results.rowCount > 0)
    {
        return res.status(200).send({
            success: 'true',
            message: 'detection(s) retrieved successfully',
            detections: results.rows
        });
    }
    return res.status(404).send({
        success: 'false',
        message: 'detection does not exist',
       });
      }
  })
  
}

// get detection by id
getDetection(req, res) {
    const id = parseInt(req.params.id, 10);
    pool.query("SELECT * FROM public.detections where id = $1",[id], (error, results) => {
    if (error) {
      console.log(error);
    } 
    else
      {
          if(results.rowCount == 1){
          return res.status(200).send({
            success: 'true',
            message: 'detection retrieved successfully',
            detections: results.rows
          });
        }
        else{
          return res.status(404).send({
          success: 'false',
          message: 'detection does not exist'
        });
        }
    }
});

}

// get detections by cid
getDetections(req, res) {
  const cid = parseInt(req.params.cid, 10);
  pool.query("SELECT * FROM public.detections where cid = $1",[cid], (error, results) => {
  if (error) {
    console.log(error);
  } 
  else
    {
        if(results.rowCount > 0){
        return res.status(200).send({
          success: 'true',
          message: 'detection(s) retrieved successfully',
          detections: results.rows
        });
      }
      else{
        return res.status(404).send({
        success: 'false',
        message: 'detection does not exist'
      });
      }
  }
});

}

// add new detection
createDetection(req, res) {
    if(!req.body.cid) {
      return res.status(400).send({
        success: 'false',
        message: 'Criminal ID is required'
      });
    } 
    else if(!req.body.time_stamp) {
      return res.status(400).send({
        success: 'false',
        message: 'Timestamp is required'
      });
    }
    else if(!req.body.location) {
        return res.status(400).send({
          success: 'false',
          message: 'Location is required'
        });
      }
    else if(!req.body.rsrc) {
    return res.status(400).send({
        success: 'false',
        message: 'Picture is required'
    });
    }  
    const location = req.body.location;
    const time_stamp = req.body.time_stamp;
    const rsrc = req.body.rsrc;
    const cid = req.body.cid;
    pool.query("INSERT INTO public.detections (time_stamp, cid, location, rsrc, valid) VALUES ($1, $2, $3, $4, $5)", [time_stamp, cid, location, rsrc, true], (error, results) => {
      if (error) {
        console.log(error);
      } 
      else{
        if(results.rowCount == 1)
        {
          return res.status(201).send({
          success: 'true',
          message: 'detection added successfully'
          });
        }
      }
    });
  
  }

// update detection  
updateDetection(req, res) {
    const id = parseInt(req.params.id, 10);

    if(!req.body.cid) {
        return res.status(400).send({
          success: 'false',
          message: 'Criminal ID is required'
        });
      } 
    else if(!req.body.time_stamp) {
        return res.status(400).send({
          success: 'false',
          message: 'timestamp is required'
        });
      }
    else if(!req.body.location) {
          return res.status(400).send({
            success: 'false',
            message: 'location is required'
          });
        }
    else if(!req.body.rsrc) {
      return res.status(400).send({
          success: 'false',
          message: 'picture is required'
      });
      }
    else if(!req.body.valid) {
      return res.status(400).send({
          success: 'false',
          message: 'validity flag is required'
      });
      }  
      const location = req.body.location;
      const time_stamp = req.body.time_stamp;
      const rsrc = req.body.rsrc;
      const cid = req.body.cid;
      const valid = req.body.valid;
      pool.query("UPDATE public.detections SET location = $1, time_stamp = $2, rsrc = $3, cid = $4, valid = $5 WHERE id = $6", [location, time_stamp, rsrc, cid, valid, id], (error, results) => {
        if (error) {
          console.log(error);
        } 

        else
        {
          if(results.rowCount == 1)
            {
              return res.status(201).send({
                  success: 'true',
                  message: 'detection updated successfully',
                  detections: results.rows
              });
            }
          return res.status(404).send({
            success: 'false',
            message: 'detection does not exist'
           });
        }
      });

}

// delete detection by id   
deleteDetection(req, res) {
    const id = parseInt(req.params.id, 10);
    pool.query("DELETE FROM public.detections where id = $1", [id], (error, results) => {
    if (error) {
      console.log(error);
    } 
    else
    {
      if(results.rowCount == 1)
        {
          return res.status(200).send({
          success: 'true',
          message: `detection with ID: ${id} DELETED successfully`
          }); 
        }
      else
       {
        return res.status(404).send({
        success: 'false',
        message: `detection with ID: ${id} does not exist`
        });
       }
    }
  });

}

}

const DetectionController = new DetectionsController();
module.exports = DetectionController;