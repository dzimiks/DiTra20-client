module.exports.tablesSelect = (req, res) => {
  // TYPES_OF_INSTITUTIONS
  // HIGH_EDUCATION_INSTITUTION
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
