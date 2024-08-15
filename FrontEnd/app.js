const apiUrl = 'http://localhost:8080/tarefas'; // URL do seu back-end

document.addEventListener('DOMContentLoaded', loadTasks);
document.getElementById('task-form').addEventListener('submit', createTask);

function loadTasks() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(tasks => {
            const taskList = document.getElementById('task-list');
            taskList.innerHTML = '';
            tasks.forEach(task => {
                const taskItem = document.createElement('li');
                taskItem.innerHTML = `
                    ${task.descricaoTarefa}
                    <div>
                        <button class="edit" onclick="editTask(${task.id})">Editar</button>
                        <button class="delete" onclick="deleteTask(${task.id})">Excluir</button>
                    </div>
                `;
                taskList.appendChild(taskItem);
            });
        });
}

function createTask(event) {
    event.preventDefault();
    const descricaoTarefa = document.getElementById('task-input').value;

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ descricaoTarefa })
    })
    .then(response => response.json())
    .then(task => {
        document.getElementById('task-input').value = '';
        loadTasks();
    });
}

function editTask(id) {
    const newDescription = prompt('Edite a tarefa:');
    if (newDescription) {
        fetch(`${apiUrl}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ descricaoTarefa: newDescription })
        })
        .then(() => loadTasks());
    }
}

function deleteTask(id) {
    fetch(`${apiUrl}/${id}`, {
        method: 'DELETE'
    })
    .then(() => loadTasks());
}