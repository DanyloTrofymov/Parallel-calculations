var downloadLink;
// Function to send matrix data to the server
function sendMatrixData(mode, matrixA, matrixB) {
    // Create a WebSocket connection to the server
    const socket = new WebSocket('ws://localhost:5000');
    var startTimer;
    var endTimer;
    // Handle the connection open event
    socket.onopen = () => {
        startTimer = new Date();

        // Create an object to hold the matrix data
        const matrixData = {
            mode: mode,
            matrixA: matrixA,
            matrixB: matrixB
        };

        socket.send(JSON.stringify(matrixData));
    };

    // Handle the message event
    socket.onmessage = (event) => {
        endTimer = new Date();
        const jsonString = event.data;

        const result = JSON.parse(jsonString);
        if (result.status == 1) {
            sendResult(JSON.parse(result.matrix));
            changeTime('Time taken: ' + (endTimer - startTimer) + ' ms')

        } else if (result.status == 0) {
            changeInfo("Error: " + result.message);
        }

        // Handle the connection close event
        socket.onclose = () => {
            changeInfo("Disconnected from server");
            console.log('Disconnected from server');
        };
    };
}

function calculate() {
    const mode = document.querySelector('input[name="mode"]:checked').value;
    changeInfo("Calculating...");
    changeTime('Time taken: ...')
    changeButtonDisabled(true);
    if (mode === 'client') {
        const matrixAFile = document.getElementById('matrixAFile').files[0];
        const matrixBFile = document.getElementById('matrixBFile').files[0];

        if (matrixAFile && matrixBFile) {
            const matrixAReader = new FileReader();
            const matrixBReader = new FileReader();

            matrixAReader.onload = function () {
                const matrixA = parseMatrixData(matrixAReader.result);

                matrixBReader.onload = function () {
                    const matrixB = parseMatrixData(matrixBReader.result);
                    sendMatrixData(mode, matrixA, matrixB);

                };
                matrixBReader.readAsText(matrixBFile);
            };
            matrixAReader.readAsText(matrixAFile);
        } else {
            alert('Please select both matrix files');
        }
    } else if (mode === 'server') {
        sendMatrixData(mode, null, null);
    }
}

function parseMatrixData(data) {
    const rows = data.trim().split('\n');
    const matrix = [];

    for (const row of rows) {
        const values = row.trim().split(' ');
        const parsedRow = values.map(value => parseInt(value, 10));
        matrix.push(parsedRow);
    }

    return matrix;
}

function downloadResult() {
    // Trigger the download link
    const downloadLink = document.querySelector('a[download]');
    downloadLink.click();
}
function changeTime(string) {
    const time = document.getElementById('time');
    time.textContent = string;
}
function changeInfo(string) {
    const info = document.getElementById('info');
    info.textContent = string;
}
function changeButtonDisabled(state) {
    const button = document.getElementById('button');
    button.disabled = state;
}

function showFileInputs(state) {
    if (state) {
        document.getElementById('matrixFilesContainer').style.display = 'block';
    } else {
        document.getElementById('matrixFilesContainer').style.display = 'none';
    }
}

function sendResult(result) {
    const resultString = result.map(row => row.join(' ')).join('\n');
    // Store the result as a file and enable downloading
    const resultBlob = new Blob([resultString], { type: 'text/plain' });
    const resultURL = URL.createObjectURL(resultBlob);
    if (downloadLink) {
        document.body.removeChild(downloadLink);
    }
    downloadLink = document.createElement('a');
    downloadLink.href = resultURL;
    downloadLink.download = 'matrix_result.txt';
    document.body.appendChild(downloadLink);
    changeButtonDisabled(false);
    changeInfo("Done!");
}