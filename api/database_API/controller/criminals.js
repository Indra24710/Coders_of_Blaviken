/* eslint-disable class-methods-use-this */
const pool = require('../connect');

class CriminalsController{
// get all criminals
getAllCriminals(req, res) {
  pool.query("SELECT * FROM public.criminals ", (error, results) => {
    if(error) {
      console.log(error);
    } 
    else {
    if(results.rowCount > 0)
    {
        return res.status(200).send({
            success: 'true',
            message: 'criminal entry(s) retrieved successfully',
            criminals: results.rows
        });
    }
    return res.status(404).send({
        success: 'false',
        message: 'criminal entry does not exist',
       });
      }
  })
  
}

// get criminal by id
getCriminal(req, res) {
    const id = parseInt(req.params.id, 10);
    pool.query("SELECT * FROM public.criminals where id = $1",[id], (error, results) => {
    if (error) {
      console.log(error);
    } 
    else
      {
          if(results.rowCount == 1){
          return res.status(200).send({
            success: 'true',
            message: 'criminal entry retrieved successfully',
            criminals: results.rows
          });
        }
        else{
          return res.status(404).send({
          success: 'false',
          message: 'criminal entry does not exist'
        });
        }
    }
});

}

}

const CriminalController = new CriminalsController();
module.exports = CriminalController;