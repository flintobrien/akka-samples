<a href="#/basicjson/new">Create a new user</a>

<table id="userlist" class="full">
	<thead>
		<tr>
			<th>Name</th></tr>
	</thead>

	<tbody id="users">
		{{#users}}
		<tr>
			<td><a href="#/basicjson/{{id}}">{{name}}</a></td></tr>
		{{/users}}
	</tbody>

</table>
