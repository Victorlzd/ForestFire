
function makeRows(rows, cols, tags) {
  container = document.getElementById("container");
  container.style.setProperty('--grid-rows', rows);
  container.style.setProperty('--grid-cols', cols);
  for (i = 0; i < rows; i++) {
    for (j = 0; j < cols; j++) {
        let cell = document.createElement("div");
        cell.id = "forest_"+i+"_"+j
        cell.innerText = i + "," + j;
        container.appendChild(cell).className = "grid-item " + tags[i][j];
    };
  };
  document.getElementById("time_buttons").hidden = false
};

function updateRows(time) {
    for (i = 0; i < window.forest_height; i++) {
      for (j = 0; j < window.forest_width; j++) {
          cell = document.getElementById("forest_"+i+"_"+j);
          cell.className = "grid-item " + window.forest[time-1][i][j];
      };
    };
  };

function add_time(a) {
    if(t+a > 0 && t+a <= window.t_max)
    t = t+a;
    document.getElementById("time_t").innerHTML = t + "/" + window.t_max;
    updateRows(t);
}

function next_time() {
    add_time(1)
}

function previous_time() {
    add_time(-1)
}

function set_time(t){
    window.t_max = t
    document.getElementById("time_t").innerHTML = "1/" + window.t_max
}

function change_file() {
    var reader = new FileReader(); // File reader to read the file 
      
      // This event listener will happen when the reader has read the file
    reader.addEventListener('load', function() {
      file_loaded(JSON.parse(reader.result))
     });
      
    reader.readAsText(upload.files[0]); // Read the uploaded file
    
    
}

function file_loaded(result) {
    set_time(result.t_max)
    window.forest = result.forest
    window.forest_height = result.height
    window.forest_width = result.width
    makeRows(result.height, result.width, result.forest[0])
}

let upload = document.getElementById('file_input')
upload.addEventListener('change', change_file);

document.getElementById("previous").addEventListener("click", previous_time)
document.getElementById("next").addEventListener("click", next_time)