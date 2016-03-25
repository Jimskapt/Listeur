package listeur.gui.filterwindow;

import java.net.URL ;
import java.util.ArrayList ;
import java.util.ResourceBundle ;
import java.util.stream.Collectors ;

import javafx.collections.FXCollections ;
import javafx.fxml.FXML ;
import javafx.scene.Node ;
import javafx.scene.control.ChoiceBox ;
import javafx.scene.control.Label ;
import javafx.scene.control.Spinner ;
import javafx.scene.control.TextArea ;
import javafx.scene.layout.GridPane ;
import javafx.scene.layout.HBox ;
import javafx.scene.layout.Pane ;
import listeur.gui.tools.CustomModalWindowCtrl ;

public class FilterWindowCtrl extends CustomModalWindowCtrl
{
	@FXML private GridPane gridElements;
	@FXML private ResourceBundle resources;
	@FXML private URL location;
	@FXML private TextArea queryCode;
	@FXML private ChoiceBox<String> typeTarget;
	
	protected int tableRows;
	
	public FilterWindowCtrl()
	{
		
	}
	
	@FXML void initialize()
	{
		assert gridElements != null : "fx:id=\"gridElements\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        assert queryCode != null : "fx:id=\"queryCode\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        assert typeTarget != null : "fx:id=\"typeTarget\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        
        typeTarget.setItems( FXCollections.observableArrayList( resources.getString( "Folders" ), resources.getString( "Files" )) );
        typeTarget.getSelectionModel().clearAndSelect( 0 );
        
        ArrayList<Row> rows=new ArrayList<>();
        
        rows.add(new Row());
        
        Row last=new Row();
        last.setLastRow( true );
        rows.add(last);
        
        tableRows=0;
        rows.stream().forEach( r ->
        {
        	Node[] elements=r.getElements();
        	
        	if( tableRows==0 )
        	{
        		elements[0]=new ChoiceBox<String>();
        		((ChoiceBox<String>)elements[0]).setItems( FXCollections.observableArrayList( resources.getString( "With" ), "" ) );
        	}
        	
        	tableRows++;
        	gridElements.addRow(tableRows, elements);
        });
        
        queryCode.setText( "SELECT FROM folders WHERE size>0" );
    }
	
	protected void compileQuery()
	{
		String query=
		gridElements.getChildren().stream()
		.map( d ->
		{
			if( ChoiceBox.class.isInstance( d ) )
				return (String)(((ChoiceBox<String>)d).getValue());
			else if( Spinner.class.isInstance( d ) )
				return (Integer)(((Spinner)d).getValue())+"";
			else if( Label.class.isInstance( d ) )
				return ((Label)d).getText();
			else if( HBox.class.isInstance( d ) )
			{
				HBox t=(HBox)d;
				
				return
				t.getChildren().stream()
				.map( tc ->
				{
					if( ChoiceBox.class.isInstance( tc ) )
						return (String)(((ChoiceBox<String>)tc).getValue());
					else if( Spinner.class.isInstance( tc ) )
						return (Integer)(((Spinner)tc).getValue())+"";
					else
						return "<???>";
				})
				.collect( Collectors.joining( " " ) );
			}
			else
				return "<???>";
		})
		.filter( d -> d!=null )
		.filter( d -> !d.equals( "" ) )
		.collect( Collectors.joining( " " ) );
		
		queryCode.setText( "SELECT FROM "+typeTarget.getValue()+" "+query );
	}
	
	
	
	
	
	
	protected class Row
	{
		protected ChoiceBox<String> operator;
		protected ChoiceBox<String> property;
		protected ChoiceBox<String> propertyOperator;
		protected Pane valueOperator;
		protected boolean lastRow;
		
		public Row()
		{
			this.lastRow=false;
			
			// ==============================
			
			operator=new ChoiceBox<String>();
			operator.setItems( FXCollections.observableArrayList( resources.getString( "and" ), resources.getString( "or" ) ) );
			operator.getSelectionModel().selectedIndexProperty().addListener( (val, bef, aft) ->
			{
				if( this.isLastRow() )
				{
					this.setLastRow( false );
					
					Row row=new Row();
					row.setLastRow( true );
					
					tableRows++;
					gridElements.addRow( tableRows, row.getElements() );
					
					gridElements.getScene().getWindow().sizeToScene();
				}
			});
			
	        property=new ChoiceBox<String>();
	        property.setItems( FXCollections.observableArrayList( resources.getString( "size" ), resources.getString( "name"), resources.getString( "childrenNumber" ) ) );
	        
	        propertyOperator=new ChoiceBox<String>();
	        propertyOperator.setItems( FXCollections.observableArrayList("=",">",">=","<","<=","!= (or <>)") );
	        
	        // ============================== 
	        
	        valueOperator=new HBox(10);
	        
	        Spinner<Integer> value=new Spinner<Integer>(0, Integer.MAX_VALUE, 0);
	        value.setEditable( true );
	        
	        ChoiceBox<String> unitSize=new ChoiceBox<>();
	        unitSize.setItems( FXCollections.observableArrayList("byte","ko","Mo","Go","To","Po") );
	        
	        valueOperator.getChildren().addAll( value, unitSize );
	        
	        // ============================== 
	        
	        value.valueProperty().addListener( (e, b, a) -> { compileQuery(); } );
	        unitSize.getSelectionModel().selectedIndexProperty().addListener( (e, b, a) -> { compileQuery(); });
	        
	        operator.getSelectionModel().selectedIndexProperty().addListener( (e, b, a) -> { compileQuery(); });
	        property.getSelectionModel().selectedIndexProperty().addListener( (e, b, a) -> { compileQuery(); });
	        propertyOperator.getSelectionModel().selectedIndexProperty().addListener( (e, b, a) -> { compileQuery(); });
		}
		
		public void setLastRow(boolean value) { this.lastRow=value; }
		
		public boolean isLastRow() { return this.lastRow; }
		
		public Node[] getElements()
		{
			return new Node[]{ operator, property, propertyOperator, valueOperator };
		}
	}
}
