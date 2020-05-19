const express = require('express');
const router = express.Router();

const mainController = require('./controllers');

// GET
router.get('/api/v1/allTables', mainController.allTablesSelect);
router.get('/api/v1/tables', mainController.tablesSelect);

// POST
router.post('/api/v1/tables', mainController.tablesInsert);

// PATCH
router.patch('/api/v1/tables', mainController.tablesUpdate);

// DELETE
router.delete('/api/v1/tables', mainController.tablesDelete);

module.exports = router;
