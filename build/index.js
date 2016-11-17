var Promise = require('bluebird');
var fs = Promise.promisifyAll(require('fs-extra'));
var path = require('path');
var xmldoc = require('xmldoc');

exports.onCreateProject = function (api, app, config, cb) {

  var copyCustomRules = function (outpath) {
    var file = 'custom_rules.xml',
      srcPath = path.join(__dirname, '../android', file),
      buildXml = fs.readFileSync(srcPath, "utf-8"),
      xmlDoc = new xmldoc.XmlDocument(buildXml);

    xmlDoc.attr.name = app.manifest.shortName;
    xmlDoc.firstChild.attr.file = path.join(__dirname, '../android',
      'devtool/crashlytics_build.xml');

    return fs.outputFileAsync(path.join(outpath, file), xmlDoc.toString(), 'utf-8');
  };

  return copyCustomRules(config.outputPath)
    .then(cb);
}; 
