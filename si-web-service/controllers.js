module.exports.test = (req, res) => {
  res.json({
    test: 'dzimiks'
  });
};

module.exports.tables = (req, res) => {
  // TYPES_OF_INSTITUTIONS
  // HIGH_EDUCATION_INSTITUTION
  const tableName = req.body.tableName || 'TYPES_OF_INSTITUTIONS';
  const query = `SELECT * FROM ${tableName}`;

  res.locals.connection.query(query, (error, results) => {
    console.log(`Running ${query}...`);

    if (error) {
      throw error;
    }

    res.send(JSON.stringify({
      status: 200,
      error: null,
      response: results
    }));
  });
};
