(function($) {

	var app = $.sammy(function() {

		var samples = [
			{ title:"Baisc JSON Service", uri:"#/basicjson", description:"This is a simple example of using Akka's REST support to provide CRUD services via JSON serialization" }
		];

		this.element_selector = "#col1_content";

		this.use(Sammy.Mustache, "ms");
		this.use(Sammy.JSON);
		this.use(Sammy.NestedParams);

		this.get("#/", function(ctx) {

			ctx.app.swap("");

			ctx.partial("templates/about.ms", {
				title:"Overview", uri:"#/", description:"This is the main navigation page for all samples"
			}, function(rendered) { 
				$("#col3_content").empty();
				$("#col3_content").append(rendered); 
			});

			ctx.partial("templates/home.ms", {samples:samples}, function(rendered) {
				ctx.$element().append(rendered);
			});
		});

		this.get("#/basicjson/new", function(ctx) {

			// creating a new user
			ctx.partial("templates/users/detail.ms", {
				id:Math.uuid(20), name:""
			});	
		});

		this.get("#/basicjson/:id", function(ctx) {

			var id = ctx.params["id"];

			// editing an existing user
			$.ajax({
				url:"/api/users/"+ctx.params["id"],
				dataType:"json",
				async:false,
				success: function(data) {
					ctx.partial("templates/users/detail.ms", data);	
				},
				error: function(error) { ctx.log(error); }
			});

		});

		this.get("#/basicjson", function(ctx) {

			ctx.app.swap("");

			ctx.partial("templates/about.ms", samples[0], function(rendered) { 
				$("#col3_content").empty();
				$("#col3_content").append(rendered); 
			});

			$.ajax({
				url:"/api/users",
				dataType:"json",
				async:false,
				success: function(data) {
					ctx.partial("templates/users/main.ms", {users:data}, function(rendered) {
						ctx.$element().append(rendered);
					});
				},
				error: function(error) { ctx.log(error); }
			});


		});

		this.put("#/basicjson/:id", function(ctx) {

			var form_fields = ctx.params;

			ctx.log(ctx.json(form_fields.obj));

			$.ajax({
				url:"/api/users/"+ctx.params["id"],
				dataType:"json",
				type:"put",
				data:ctx.json(form_fields.obj),
				contentType:"application/json",
				processData:false,
				success: function(data) {
					ctx.redirect("#/basicjson");
				},
				error: function(error) {
					ctx.log(error);
				}
			});

		});

	});

	$(function() {
		app.run("#/");
	});

})(jQuery);
