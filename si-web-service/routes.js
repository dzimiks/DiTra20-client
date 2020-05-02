const express = require('express');
const router = express.Router();

const mainController = require('./controllers');

// GET
router.get('/', mainController.test);
router.get('/api/v1/tables', mainController.tables);

// POST
router.post('/api/v1/tables', (req, res) => {
  res.json({ tableName: req.body.tableName });
});

module.exports = router;
