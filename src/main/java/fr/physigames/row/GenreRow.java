package fr.physigames.row;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreRow {
    private Long id;
    private String code;
    private String name;
}
