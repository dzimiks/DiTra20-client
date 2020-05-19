require('dotenv').config();
const axios = require('axios');

// Default axios baseURL
axios.defaults.baseURL = 'http://localhost:3001/api/v1';

// Query: SELECT table_name FROM information_schema.tables WHERE table_schema = 'tim_403_2_si2019'
module.exports.allTablesSelect = (req, res) => {
  const query = `SELECT table_name FROM information_schema.tables WHERE table_schema = '${process.env.SQL_DB_NAME}'`;

  res.locals.connection.query(query, (error, results) => {
    console.log(`Running query: ${query}`);

    if (error) {
      throw error;
    }

    // TODO: [MongoDB]: Create collection with table name
    results.forEach(name => {
      axios.post(`/posts/${name.TABLE_NAME}`, null);
    });

    res.send(JSON.stringify({ data: results }));
  });
};

// {
//     "tableName": "TYPES_OF_INSTITUTIONS"
// }
//
// Query: SELECT * FROM TYPES_OF_INSTITUTIONS
module.exports.tablesSelect = (req, res) => {
  const tableName = req.body.tableName || 'TYPES_OF_INSTITUTIONS';
  const query = `SELECT * FROM ${tableName}`;

  res.locals.connection.query(query, (error, results) => {
    console.log(`Running query: ${query}`);

    if (error) {
      throw error;
    }

    // TODO: [MongoDB]: Insert document(s) in collection
    axios.post(`/posts/insert/${tableName}`, { data: results });
    res.send(JSON.stringify({ data: results }));
  });
};

// {
//     "tableName": "TYPES_OF_INSTITUTIONS",
//     "value": {
//         "TIP_UST": "MX"
//     }
// }
//
// Query: DELETE FROM TYPES_OF_INSTITUTIONS WHERE TIP_UST='MX'
module.exports.tablesDelete = (req, res) => {
  const tableName = req.body.tableName || 'TYPES_OF_INSTITUTIONS';
  const value = req.body.value || { 'TIP_UST': 'MX' };
  let query = `DELETE FROM ${tableName} WHERE `;

  for (const val of Object.keys(value)) {
    query += `${val}='${value[val]}' AND `;
  }

  query = query.substring(0, query.length - 5);

  res.locals.connection.query(query, (error, results) => {
    console.log(`Running query: ${query}`);

    if (error) {
      throw error;
    }

    axios.delete(`/posts/${tableName}`, { data: value });
    res.send(JSON.stringify({ data: results }));
  });
};

// {
//     "tableName": "TYPES_OF_INSTITUTIONS",
//     "value": {
//         "TIP_UST": "MX",
//         "TIP_NAZIV": "Red Bull"
//     }
// }
//
// Query: INSERT INTO TYPES_OF_INSTITUTIONS (TIP_UST,TIP_NAZIV) VALUES ('MX','Red Bull')
module.exports.tablesInsert = (req, res) => {
  const tableName = req.body.tableName || 'TYPES_OF_INSTITUTIONS';
  const value = req.body.value || { 'TIP_UST': 'MX', 'TIP_NAZIV': 'Red Bull' };

  let columns = '(';
  let values = '(';

  for (const val of Object.keys(value)) {
    columns += `${val},`;
    values += `'${value[val]}',`;
  }

  columns = columns.substring(0, columns.length - 1) + ')';
  values = values.substring(0, values.length - 1) + ')';

  const query = `INSERT INTO ${tableName} ${columns} VALUES ${values}`;

  res.locals.connection.query(query, (error, results) => {
    console.log(`Running query: ${query}`);

    if (error) {
      throw error;
    }

    axios.post(`/posts/insert/${tableName}`, { data: value });
    res.send(JSON.stringify({ data: results }));
  });
};

// {
//     "tableName": "TYPES_OF_INSTITUTIONS",
//     "value": {
//         "TIP_UST": "TT",
//         "TIP_NAZIV": "Energy"
//     },
//     "condition": {
//     	   "TIP_UST": "PK"
//     }
// }
//
// Query: UPDATE TYPES_OF_INSTITUTIONS SET TIP_UST='TT',TIP_NAZIV='Energy' WHERE TIP_UST='PK'
module.exports.tablesUpdate = (req, res) => {
  const tableName = req.body.tableName || 'TYPES_OF_INSTITUTIONS';
  const value = req.body.value || { 'TIP_UST': 'TT', 'TIP_NAZIV': 'Energy' };
  const condition = req.body.condition || { 'TIP_UST': 'PK' };
  let query = `UPDATE ${tableName} SET `;

  for (const val of Object.keys(value)) {
    query += `${val}='${value[val]}',`;
  }

  query = `${query.substring(0, query.length - 1)} WHERE `;

  for (const val of Object.keys(condition)) {
    query += `${val}='${condition[val]}',`;
  }

  query = query.substring(0, query.length - 1);

  res.locals.connection.query(query, (error, results) => {
    console.log(`Running query: ${query}`);

    if (error) {
      throw error;
    }

    axios.patch(`/posts/${tableName}`, { data: { value, condition } });
    res.send(JSON.stringify({ data: results }));
  });
};
