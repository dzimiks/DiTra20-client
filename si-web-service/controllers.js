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

    res.send(JSON.stringify({ response: results }));
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

    res.send(JSON.stringify({ response: results }));
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

    res.send(JSON.stringify({ response: results }));
  });
};

// {
//     "tableName": "TYPES_OF_INSTITUTIONS",
//     "value": {
//         "TIP_UST": "TT",
//         "TIP_NAZIV": "Energy"
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

    res.send(JSON.stringify({ response: results }));
  });
};
