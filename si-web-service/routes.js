const express = require('express');
const router = express.Router();

const mainController = require('./controllers');

// GET
router.get('/api/v1/tables/select', mainController.tablesSelect);
router.get('/api/v1/tables/delete', mainController.tablesDelete);
router.get('/api/v1/tables/insert', mainController.tablesInsert);

// POST
router.post('/api/v1/tables/select', (req, res) => {
  res.json({ tableName: req.body.tableName });
});

router.post('/api/v1/tables/delete', (req, res) => {
  res.json({
    tableName: req.body.tableName,
    value: res.body
  });
});

router.post('/api/v1/tables/insert', (req, res) => {
  res.json({
    tableName: req.body.tableName,
    value: res.body
  });
});

module.exports = router;
