const { Pool } = require('pg');

// Create connection to database
const pool = new Pool({
  user: "wievmwebfpihpt",
  host: "ec2-34-197-141-7.compute-1.amazonaws.com",
  database: "dbduur4vdlukem",
  password: "5a91f479409d0c0c4be99fb3a02862b995ca0663e7072d46937ea9a4719591a9",
  port: 5432,
  ssl: {
         rejectUnauthorized: false
       }
})

// const client = new Client({
//   connectionString: process.env.DATABASE_URL||"postgres://wievmwebfpihpt:5a91f479409d0c0c4be99fb3a02862b995ca0663e7072d46937ea9a4719591a9@ec2-34-197-141-7.compute-1.amazonaws.com:5432/dbduur4vdlukem" ,
//   ssl: {
//     rejectUnauthorized: false
//   }
// });

// client.connect();
// console.log('Connected')

// pool.query("CREATE TABLE detections ()", (err, res)=>{
//   console.log(err, res)
//   pool.end()
// })
module.exports = pool;