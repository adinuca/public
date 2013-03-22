<%@ tag language="java"%>
<form name="input" action="login.htm" method="post">
	<table>
		<tr>
			<td colspan="2"> <div style="color:red">${loginErrorMessage}</div>  </td>	
		</tr>
		
		<tr>
			<td colspan="2">Please log in...</td>
			
		</tr>
		<tr>
			<td> User name:</td>
			<td><input type="text" name="username" /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="password" name="password" /></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><input type="submit" name="login"
				value="Log In..." /></td>
			<td></td>
			<td></td>
		</tr>
	</table>
</form>
