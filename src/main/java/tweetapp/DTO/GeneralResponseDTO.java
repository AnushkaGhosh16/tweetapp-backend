package tweetapp.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tweetapp.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponseDTO {
	private String message;
	private boolean isError;
}
