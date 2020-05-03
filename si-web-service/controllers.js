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
