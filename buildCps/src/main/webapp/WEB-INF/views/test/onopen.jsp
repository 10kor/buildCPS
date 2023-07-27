  if (!FileSystem.isFolder(FileSystem.getTemporaryFolder())) {
    FileSystem.makeFolder(FileSystem.getTemporaryFolder());
  }
  temporaryFolder = FileSystem.getTemporaryFile("excel-worksheet");
  FileSystem.removeFolderRecursive(temporaryFolder);
  FileSystem.makeFolder(temporaryFolder);
 
  // folder tree create
  var folderNames = [];
  folderNames[0] = "_rels";
  folderNames[1] = "docProps";
  folderNames[2] = "xl";
  folderNames[3] = "xl\\_rels";
  folderNames[4] = "xl\\drawings";
  folderNames[5] = "xl\\media";
  folderNames[6] = "xl\\printerSettings";
  folderNames[7] = "xl\\theme";
  folderNames[8] = "xl\\worksheets";
  folderNames[9] = "xl\\drawings\\_rels";
  folderNames[10] = "xl\\worksheets\\_rels";
  
  for (var i = 0; i < folderNames.length; i++) {
    var folderPath = FileSystem.getCombinedPath(temporaryFolder, folderNames[i]);
    FileSystem.makeFolder(folderPath);
  }

  // xml file create
  var fileNames = [];
  fileNames[0] = "[Content_Types].xml";
  fileNames[1] = "_rels\\.rels";
  fileNames[2] = "docProps\\app.xml";
  fileNames[3] = "docProps\\core.xml";
  fileNames[4] = "xl\\sharedStrings.xml";
  fileNames[5] = "xl\\styles.xml";
  fileNames[6] = "xl\\workbook.xml";
  fileNames[7] = "xl\\_rels\\workbook.xml.rels";
  fileNames[8] = "xl\\drawings\\drawing1.xml";
  fileNames[9] = "xl\\drawings\\_rels\\drawing1.xml.rels";
  fileNames[10] = "xl\\theme\\theme1.xml";
  fileNames[11] = "xl\\worksheets\\sheet1.xml";
  fileNames[12] = "xl\\worksheets\\_rels\\sheet1.xml.rels";
  fileNames[13] = "xl\\worksheets\\sheet2.xml";
  fileNames[14] = "xl\\worksheets\\_rels\\sheet2.xml.rels";
  
  for (var i = 0; i < fileNames.length; i++) {
    var pathe = FileSystem.getCombinedPath(temporaryFolder, fileNames[i]);
    var xmlFile = new TextFile(pathe, true, "utf-8");
    var patha = "/make?user=" + getProperty("client_ID") + "&step=" + i;
    xmlFile.write(http_Req(patha));
    xmlFile.close();
  }

  // image file create
  var pathe = FileSystem.getCombinedPath(temporaryFolder, "xl\\media\\image1.png");
  var imgFile = new TextFile(pathe, true, "utf-8");
  imgFile.write("123");
  imgFile.close();
  
  programInfo = getProgramInfo();

  programInfo["program.jobDescription"] = hasGlobalParameter("job-description") ? getGlobalParameter("job-description") : "";
  programInfo["program.partPath"] = hasGlobalParameter("document-path") ? getGlobalParameter("document-path") : "";
  programInfo["program.partName"] = FileSystem.getFilename(programInfo["program.partPath"]);
  programInfo["program.user"] = hasGlobalParameter("username") ? getGlobalParameter("username") : "";
  programInfo["program.host"] = hasGlobalParameter("hostname") ? getGlobalParameter("hostname") : "";
  
  var workpiece = getWorkpiece();
  var delta = Vector.diff(workpiece.upper, workpiece.lower);
  programInfo["program.stockLowerX"] = workpiece.lower.x;
  programInfo["program.stockLowerY"] = workpiece.lower.y;
  programInfo["program.stockLowerZ"] = workpiece.lower.z;
  programInfo["program.stockUpperX"] = workpiece.upper.x;
  programInfo["program.stockUpperY"] = workpiece.upper.y;
  programInfo["program.stockUpperZ"] = workpiece.upper.z;
  programInfo["program.stockDX"] = delta.x;
  programInfo["program.stockDY"] = delta.y;
  programInfo["program.stockDZ"] = delta.z;

  var partLowerX = hasGlobalParameter("part-lower-x") ? getGlobalParameter("part-lower-x") : 0;
  var partLowerY = hasGlobalParameter("part-lower-y") ? getGlobalParameter("part-lower-y") : 0;
  var partLowerZ = hasGlobalParameter("part-lower-z") ? getGlobalParameter("part-lower-z") : 0;
  var partUpperX = hasGlobalParameter("part-upper-x") ? getGlobalParameter("part-upper-x") : 0;
  var partUpperY = hasGlobalParameter("part-upper-y") ? getGlobalParameter("part-upper-y") : 0;
  var partUpperZ = hasGlobalParameter("part-upper-z") ? getGlobalParameter("part-upper-z") : 0;

  programInfo["program.partLowerX"] = partLowerX;
  programInfo["program.partLowerY"] = partLowerY;
  programInfo["program.partLowerZ"] = partLowerZ;
  programInfo["program.partUpperX"] = partUpperX;
  programInfo["program.partUpperY"] = partUpperY;
  programInfo["program.partUpperZ"] = partUpperZ;
  programInfo["program.partDX"] = partUpperX - partLowerX;
  programInfo["program.partDY"] = partUpperY - partLowerY;
  programInfo["program.partDZ"] = partUpperZ - partLowerZ;

  toolInfo = getToolInfo();
  
  cachedParameters = {};
  
  var currentSum = 0;
  for(var i=0;i < toolNoArray.length;i++){
    var TCT = toolCycleTimeArray[i];
    if(toolNoArray[i]!=toolNoArray[i+1]){
      currentSum += TCT;
      cycleTimeResult.push(currentSum);
      currentSum = 0;
    }else{
      currentSum += TCT;
    }
  }